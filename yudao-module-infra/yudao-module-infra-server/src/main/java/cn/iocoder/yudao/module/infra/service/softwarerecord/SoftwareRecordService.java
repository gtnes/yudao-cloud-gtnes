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
}