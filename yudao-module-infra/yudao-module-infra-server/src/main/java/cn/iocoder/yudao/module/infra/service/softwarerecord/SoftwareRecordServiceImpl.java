package cn.iocoder.yudao.module.infra.service.softwarerecord;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.infra.controller.admin.softwarerecord.vo.*;
import cn.iocoder.yudao.module.infra.dal.dataobject.softwarerecord.SoftwareRecordDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.infra.dal.mysql.softwarerecord.SoftwareRecordMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.infra.enums.ErrorCodeConstants.*;

import java.time.LocalDateTime;
import cn.iocoder.yudao.framework.ip.core.utils.IPUtils;
import cn.iocoder.yudao.framework.ip.core.utils.AreaUtils;
import cn.iocoder.yudao.framework.ip.core.Area;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 软件使用记录 Service 实现类
 *
 * @author gtnes
 */
@Service
@Validated
public class SoftwareRecordServiceImpl implements SoftwareRecordService {

    private static final Logger log = LoggerFactory.getLogger(SoftwareRecordServiceImpl.class);

    @Resource
    private SoftwareRecordMapper softwareRecordMapper;

    @Override
    public Long createSoftwareRecord(SoftwareRecordSaveReqVO createReqVO) {
        // 插入
        SoftwareRecordDO softwareRecord = BeanUtils.toBean(createReqVO, SoftwareRecordDO.class);
        softwareRecordMapper.insert(softwareRecord);
        // 返回
        return softwareRecord.getId();
    }

    @Override
    public void updateSoftwareRecord(SoftwareRecordSaveReqVO updateReqVO) {
        // 校验存在
        validateSoftwareRecordExists(updateReqVO.getId());
        // 更新
        SoftwareRecordDO updateObj = BeanUtils.toBean(updateReqVO, SoftwareRecordDO.class);
        softwareRecordMapper.updateById(updateObj);
    }

    @Override
    public void deleteSoftwareRecord(Long id) {
        // 校验存在
        validateSoftwareRecordExists(id);
        // 删除
        softwareRecordMapper.deleteById(id);
    }

    @Override
        public void deleteSoftwareRecordListByIds(List<Long> ids) {
        // 校验存在
        validateSoftwareRecordExists(ids);
        // 删除
        softwareRecordMapper.deleteByIds(ids);
        }

    private void validateSoftwareRecordExists(List<Long> ids) {
        List<SoftwareRecordDO> list = softwareRecordMapper.selectByIds(ids);
        if (CollUtil.isEmpty(list) || list.size() != ids.size()) {
            throw exception(SOFTWARE_RECORD_NOT_EXISTS);
        }
    }

    private void validateSoftwareRecordExists(Long id) {
        if (softwareRecordMapper.selectById(id) == null) {
            throw exception(SOFTWARE_RECORD_NOT_EXISTS);
        }
    }

    @Override
    public SoftwareRecordDO getSoftwareRecord(Long id) {
        return softwareRecordMapper.selectById(id);
    }

    @Override
    public PageResult<SoftwareRecordDO> getSoftwareRecordPage(SoftwareRecordPageReqVO pageReqVO) {
        return softwareRecordMapper.selectPage(pageReqVO);
    }

    /**
     * 根据设备指纹和IP创建或更新软件使用记录
     *
     * @param deviceFingerprint 设备指纹
     * @param ip 客户端IP
     * @param platform 平台编号
     * @param bit 操作系统架构
     * @param platformName 平台名称
     * @param osRelease 操作系统版本
     * @param appType 应用类型
     * @param appVersion 应用版本
     */
    @Override
    public void createOrUpdateByFingerprintAndIp(String deviceFingerprint, String ip, Integer platform, Integer bit, String platformName, String osRelease, Integer appType, String appVersion) {
        // 用设备指纹查询
        SoftwareRecordDO record = softwareRecordMapper.selectByFingerprint(deviceFingerprint);
        // ip = "36.142.60.249"; // 测试用IP
        if (record != null) {
            // 更新
            record.setPlatform(platform);
            record.setBit(bit);
            record.setOsRelease(osRelease);
            record.setAppType(appType);
            record.setVersion(appVersion);
            // 检查IP是否发生变化，避免重复更新
            String oldIp = record.getIp();
            if (!ip.equals(oldIp)) {
                record.setIp(ip); // 更新IP
                // 归属地解析
                record.setIpHomeLocation(parseIpHomeLocation(ip));
                log.info("[====== IP INFO ======] IP发生变化，从 {} 更新为 {}，重新解析归属地", oldIp, ip);
            } else {
                log.info("[====== IP INFO ======] IP未发生变化，跳过IP和归属地更新");
            }
            // 总更新次数
            record.setTotalUpdateCount(record.getTotalUpdateCount() == null ? 1 : record.getTotalUpdateCount() + 1);
            // 今日更新次数(判断更新时间的日期是否等于今天)
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime lastUpdateTime = record.getUpdateTime(); // 获取原来的更新时间
            if (lastUpdateTime != null && 
                lastUpdateTime.toLocalDate().equals(now.toLocalDate())) {
                // 如果是今天，今日更新次数+1
                record.setTodayUpdateCount(record.getTodayUpdateCount() == null ? 1 : record.getTodayUpdateCount() + 1);
            } else {
                // 如果不是今天，今日更新次数重置为1
                record.setTodayUpdateCount(1);
            }
            record.setUpdateTime(now); // 设置新的更新时间
            softwareRecordMapper.updateById(record);
        } else {
            // 新增
            SoftwareRecordDO newRecord = new SoftwareRecordDO();
            newRecord.setDeviceFingerprint(deviceFingerprint);
            newRecord.setIp(ip);
            newRecord.setPlatform(platform);
            newRecord.setBit(bit);
            newRecord.setOsRelease(osRelease);
            newRecord.setAppType(appType);
            newRecord.setVersion(appVersion);
            // 新增逻辑：初始化更新次数
            newRecord.setTotalUpdateCount(1);
            newRecord.setTodayUpdateCount(1);
            // 归属地解析
            newRecord.setIpHomeLocation(parseIpHomeLocation(ip));
            softwareRecordMapper.insert(newRecord);
        }
    }

    /**
     * 解析IP归属地
     *
     * @param ip IP地址
     * @return 归属地字符串，解析失败返回null
     */
    private String parseIpHomeLocation(String ip) {
        String ipHomeLocation = null;
        try {
            // 尝试使用第三方IP解析服务获取更详细的信息
            String detailedLocation = getDetailedIpLocation(ip);
            if (detailedLocation != null && !detailedLocation.isEmpty()) {
                ipHomeLocation = detailedLocation;
                log.info("第三方IP解析结果: {}", ipHomeLocation);
            } else {
                // 回退到原来的方法
                log.warn("第三方IP解析失败，用本地库解析ip");
                Area area = IPUtils.getArea(ip);
                if (area != null) {
                    ipHomeLocation = AreaUtils.formatFullPath(area, " ", true);
                    if (ipHomeLocation == null || ipHomeLocation.isEmpty() || ipHomeLocation.equals("全球 中国")) {
                        ipHomeLocation = AreaUtils.format(area.getId(), " ");
                    }
                }
            }
        } catch (Exception e) {
            log.error("IP归属地解析失败: {}", e.getMessage(), e);
            ipHomeLocation = null;
        }
        return ipHomeLocation;
    }

    /**
     * 使用第三方服务获取详细的IP归属地信息
     *
     * @param ip IP地址
     * @return 详细归属地信息
     */
    private String getDetailedIpLocation(String ip) {
        try {
            // 这里可以集成第三方IP解析服务，比如：
            // 1. ip-api.com
            // 2. ipinfo.io
            // 3. 阿里云IP地址库
            // 4. 腾讯云IP地址库

            // 示例：使用ip-api.com的免费服务
            String url = "http://ip-api.com/json/" + ip + "?fields=status,message,country,regionName,city,isp&lang=zh-CN";
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            if (response != null && response.contains("\"status\":\"success\"")) {
                // 简单的JSON解析（实际项目中建议使用Jackson或Gson）
                String country = extractJsonValue(response, "country");
                String region = extractJsonValue(response, "regionName");
                String city = extractJsonValue(response, "city");
                String isp = extractJsonValue(response, "isp");

                StringBuilder sb = new StringBuilder();
                if (country != null && !country.isEmpty()) {
                    sb.append(country);
                }
                if (region != null && !region.isEmpty()) {
                    if (sb.length() > 0) sb.append(" ");
                    sb.append(region);
                }
                if (city != null && !city.isEmpty()) {
                    if (sb.length() > 0) sb.append(" ");
                    sb.append(city);
                }
                if (isp != null && !isp.isEmpty()) {
                    if (sb.length() > 0) sb.append(" ");
                    sb.append(isp);
                }

                return sb.toString();
            }
        } catch (Exception e) {
            log.warn("第三方IP解析服务调用失败: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 简单的JSON值提取方法
     *
     * @param json JSON字符串
     * @param key 键名
     * @return 值
     */
    private String extractJsonValue(String json, String key) {
        String pattern = "\"" + key + "\":\"([^\"]*)\"";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = p.matcher(json);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }
}