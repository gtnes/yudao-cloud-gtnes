package cn.iocoder.yudao.module.infra.service.softwarerecord;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.infra.controller.admin.softwarerecord.vo.*;
import cn.iocoder.yudao.module.infra.dal.dataobject.softwarerecord.SoftwareRecordDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 软件使用记录 Service 接口
 *
 * @author gtnes
 */
public interface SoftwareRecordService {

    /**
     * 创建软件使用记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSoftwareRecord(@Valid SoftwareRecordSaveReqVO createReqVO);

    /**
     * 更新软件使用记录
     *
     * @param updateReqVO 更新信息
     */
    void updateSoftwareRecord(@Valid SoftwareRecordSaveReqVO updateReqVO);

    /**
     * 删除软件使用记录
     *
     * @param id 编号
     */
    void deleteSoftwareRecord(Long id);

    /**
    * 批量删除软件使用记录
    *
    * @param ids 编号
    */
    void deleteSoftwareRecordListByIds(List<Long> ids);

    /**
     * 获得软件使用记录
     *
     * @param id 编号
     * @return 软件使用记录
     */
    SoftwareRecordDO getSoftwareRecord(Long id);

    /**
     * 获得软件使用记录分页
     *
     * @param pageReqVO 分页查询
     * @return 软件使用记录分页
     */
    PageResult<SoftwareRecordDO> getSoftwareRecordPage(SoftwareRecordPageReqVO pageReqVO);

    /**
     * 根据deviceFingerprint和ip新增或更新软件使用记录
     */
    void createOrUpdateByFingerprintAndIp(String deviceFingerprint, String ip, Integer platform, Integer bit, String platformName, String osRelease, Integer appType, String appVersion);

    /**
     * 获取软件使用记录统计数据
     * <p>
     * 根据软件类别统计相关数据：
     * <ul>
     *   <li>今日新增合计（创建日期为今天的数据条数）</li>
     *   <li>今日更新合计（更新日期为今天的数据条数）</li>
     *   <li>数据总数（统计没有被删除的数据条数）</li>
     *   <li>平台设备总数（软件平台windows、Linux、Macos分别有多少条数据）</li>
     * </ul>
     * <p>
     * 该方法会调用6个Mapper查询方法来获取数据：
     * <ul>
     *   <li>selectTodayNewCount - 统计今日新增</li>
     *   <li>selectTodayUpdateCount - 统计今日更新</li>
     *   <li>selectTotalCount - 统计数据总数</li>
     *   <li>selectWindowsCount - 统计Windows平台数量</li>
     *   <li>selectLinuxCount - 统计Linux平台数量</li>
     *   <li>selectMacOSCount - 统计MacOS平台数量</li>
     * </ul>
     *
     * @param reqVO 请求参数，包含软件类别字段 appType
     * @return 统计数据，包含今日新增、今日更新、数据总数和各平台数量
     */
    SoftwareRecordStatisticsRespVO getStatistics(SoftwareRecordStatisticsReqVO reqVO);
}