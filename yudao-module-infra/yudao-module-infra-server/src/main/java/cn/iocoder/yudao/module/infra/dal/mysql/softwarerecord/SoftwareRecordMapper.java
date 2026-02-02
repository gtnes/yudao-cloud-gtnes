package cn.iocoder.yudao.module.infra.dal.mysql.softwarerecord;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.infra.dal.dataobject.softwarerecord.SoftwareRecordDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
    SoftwareRecordDO selectByFingerprint(String deviceFingerprint);

    /**
     * 查询今日新增合计
     * <p>
     * 统计创建日期为今天的数据条数
     * <p>
     * SQL: SELECT COUNT(*) FROM infra_gtnes_software_record
     *      WHERE app_type = #{appType} AND deleted = 0
     *      AND DATE(create_time) = CURDATE()
     *
     * @param appType 软件类别
     * @return 今日新增条数
     */
    Long selectTodayNewCount(@Param("appType") Integer appType);

    /**
     * 查询今日更新合计
     * <p>
     * 统计更新日期为今天的数据条数
     * <p>
     * SQL: SELECT COUNT(*) FROM infra_gtnes_software_record
     *      WHERE app_type = #{appType} AND deleted = 0
     *      AND DATE(update_time) = CURDATE()
     *
     * @param appType 软件类别
     * @return 今日更新条数
     */
    Long selectTodayUpdateCount(@Param("appType") Integer appType);

    /**
     * 查询数据总数
     * <p>
     * 统计没有被删除的数据条数
     * <p>
     * SQL: SELECT COUNT(*) FROM infra_gtnes_software_record
     *      WHERE app_type = #{appType} AND deleted = 0
     *
     * @param appType 软件类别
     * @return 数据总数
     */
    Long selectTotalCount(@Param("appType") Integer appType);

    /**
     * 查询Windows平台总数
     * <p>
     * 统计软件平台为Windows的数据条数（platform=1）
     * <p>
     * SQL: SELECT COUNT(*) FROM infra_gtnes_software_record
     *      WHERE app_type = #{appType} AND platform = 1 AND deleted = 0
     *
     * @param appType 软件类别
     * @return Windows平台总数
     */
    Long selectWindowsCount(@Param("appType") Integer appType);

    /**
     * 查询Linux平台总数
     * <p>
     * 统计软件平台为Linux的数据条数（platform=5）
     * <p>
     * SQL: SELECT COUNT(*) FROM infra_gtnes_software_record
     *      WHERE app_type = #{appType} AND platform = 5 AND deleted = 0
     *
     * @param appType 软件类别
     * @return Linux平台总数
     */
    Long selectLinuxCount(@Param("appType") Integer appType);

    /**
     * 查询MacOS平台总数
     * <p>
     * 统计软件平台为MacOS的数据条数（platform=3）
     * <p>
     * SQL: SELECT COUNT(*) FROM infra_gtnes_software_record
     *      WHERE app_type = #{appType} AND platform = 3 AND deleted = 0
     *
     * @param appType 软件类别
     * @return MacOS平台总数
     */
    Long selectMacOSCount(@Param("appType") Integer appType);
}