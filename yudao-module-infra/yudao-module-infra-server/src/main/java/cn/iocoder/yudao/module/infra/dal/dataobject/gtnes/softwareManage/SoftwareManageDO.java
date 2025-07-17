package cn.iocoder.yudao.module.infra.dal.dataobject.gtnes.softwareManage;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 软件管理 DO
 *
 * @author gtnes
 */
@TableName("infra_gtnes_software_manage")
@KeySequence("infra_gtnes_software_manage_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SoftwareManageDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 软件名称
     */
    private String name;
    /**
     * 版本号
     */
    private String version;
    /**
     * 软件平台
     *
     * 枚举 {@link TODO software_platform 对应的类}
     */
    private Byte platform;
    /**
     * 位版本
     *
     * 枚举 {@link TODO software_bit 对应的类}
     */
    private Byte bit;
    /**
     * 简介
     */
    private String description;
    /**
     * 下载地址
     */
    private String downloadUrl;


}