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

/**
 * 软件使用记录 Service 实现类
 *
 * @author gtnes
 */
@Service
@Validated
public class SoftwareRecordServiceImpl implements SoftwareRecordService {

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

    @Override
    public void createOrUpdateByFingerprintAndIp(String deviceFingerprint, String ip, Integer platform, Integer bit, String platformName, String osRelease, Integer appType, String appVersion) {
        SoftwareRecordDO record = softwareRecordMapper.selectByFingerprintAndIp(deviceFingerprint, ip);
        if (record != null) {
            // 更新
            record.setPlatform(platform);
            record.setBit(bit);
            record.setOsRelease(osRelease);
            record.setAppType(appType);
            record.setVersion(appVersion);
            record.setUpdateTime(LocalDateTime.now()); // 手动更新时间
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
            softwareRecordMapper.insert(newRecord);
        }
    }
}