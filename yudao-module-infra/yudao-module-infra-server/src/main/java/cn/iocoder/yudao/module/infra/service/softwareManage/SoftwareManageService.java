package cn.iocoder.yudao.module.infra.service.softwareManage;

import java.util.*;

import cn.iocoder.yudao.module.infra.controller.admin.softwareManage.vo.SoftwareManagePageReqVO;
import cn.iocoder.yudao.module.infra.controller.admin.softwareManage.vo.SoftwareManageSaveReqVO;
import jakarta.validation.*;
import cn.iocoder.yudao.module.infra.dal.dataobject.softwareManage.SoftwareManageDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 软件管理 Service 接口
 *
 * @author gtnes
 */
public interface SoftwareManageService {

    /**
     * 创建软件管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSoftwareManage(@Valid SoftwareManageSaveReqVO createReqVO);

    /**
     * 更新软件管理
     *
     * @param updateReqVO 更新信息
     */
    void updateSoftwareManage(@Valid SoftwareManageSaveReqVO updateReqVO);

    /**
     * 删除软件管理
     *
     * @param id 编号
     */
    void deleteSoftwareManage(Long id);

    /**
    * 批量删除软件管理
    *
    * @param ids 编号
    */
    void deleteSoftwareManageListByIds(List<Long> ids);

    /**
     * 获得软件管理
     *
     * @param id 编号
     * @return 软件管理
     */
    SoftwareManageDO getSoftwareManage(Long id);

    /**
     * 获得软件管理分页
     *
     * @param pageReqVO 分页查询
     * @return 软件管理分页
     */
    PageResult<SoftwareManageDO> getSoftwareManagePage(SoftwareManagePageReqVO pageReqVO);

    /**
     * 检查是否有新版本
     * @param name 软件名称
     * @param version 当前版本号
     * @param platform 平台
     * @return 新版本列表
     */
    List<SoftwareManageDO> getNewVersions(String name, String version, Byte platform);
}