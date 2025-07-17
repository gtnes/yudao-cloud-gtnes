package cn.iocoder.yudao.module.infra.dal.mysql.gtnes.softwareManage;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.infra.dal.dataobject.gtnes.softwareManage.SoftwareManageDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.infra.controller.admin.gtnes.softwareManage.vo.*;

/**
 * 软件管理 Mapper
 *
 * @author gtnes
 */
@Mapper
public interface SoftwareManageMapper extends BaseMapperX<SoftwareManageDO> {

    default PageResult<SoftwareManageDO> selectPage(SoftwareManagePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SoftwareManageDO>()
                .likeIfPresent(SoftwareManageDO::getName, reqVO.getName())
                .eqIfPresent(SoftwareManageDO::getVersion, reqVO.getVersion())
                .eqIfPresent(SoftwareManageDO::getPlatform, reqVO.getPlatform())
                .betweenIfPresent(SoftwareManageDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SoftwareManageDO::getId));
    }

    /**
     * 根据软件名称和平台查询所有版本
     */
    default List<SoftwareManageDO> selectListByNameAndPlatform(String name, Byte platform) {
        return selectList(new LambdaQueryWrapperX<SoftwareManageDO>()
                .eqIfPresent(SoftwareManageDO::getName, name)
                .eqIfPresent(SoftwareManageDO::getPlatform, platform));
    }
}