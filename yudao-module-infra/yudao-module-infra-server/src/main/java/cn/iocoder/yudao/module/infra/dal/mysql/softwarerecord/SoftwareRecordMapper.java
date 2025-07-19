package cn.iocoder.yudao.module.infra.dal.mysql.softwarerecord;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.infra.dal.dataobject.softwarerecord.SoftwareRecordDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.infra.controller.admin.softwarerecord.vo.*;

/**
 * 软件使用记录 Mapper
 *
 * @author gtnes
 */
@Mapper
public interface SoftwareRecordMapper extends BaseMapperX<SoftwareRecordDO> {

    default PageResult<SoftwareRecordDO> selectPage(SoftwareRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SoftwareRecordDO>()
                .eqIfPresent(SoftwareRecordDO::getAppType, reqVO.getAppType())
                .eqIfPresent(SoftwareRecordDO::getVersion, reqVO.getVersion())
                .eqIfPresent(SoftwareRecordDO::getPlatform, reqVO.getPlatform())
                .eqIfPresent(SoftwareRecordDO::getBit, reqVO.getBit())
                .eqIfPresent(SoftwareRecordDO::getDeviceFingerprint, reqVO.getDeviceFingerprint())
                .eqIfPresent(SoftwareRecordDO::getOsRelease, reqVO.getOsRelease())
                .betweenIfPresent(SoftwareRecordDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SoftwareRecordDO::getId));
    }

    SoftwareRecordDO selectByFingerprintAndIp(String deviceFingerprint, String ip);
}