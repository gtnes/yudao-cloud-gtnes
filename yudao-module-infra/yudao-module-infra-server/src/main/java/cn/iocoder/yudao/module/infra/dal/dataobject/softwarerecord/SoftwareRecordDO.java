package cn.iocoder.yudao.module.infra.dal.dataobject.softwarerecord;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 软件使用记录 DO
 *
 * @author gtnes
 */
@TableName("infra_gtnes_software_record")
@KeySequence("infra_gtnes_software_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SoftwareRecordDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * IP地址
     */
    private String ip;
    /**
     * IP归属地
     */
    private String ipHomeLocation;
    /**
     * 软件类别 1=9GridTools
     *
     * 枚举 {@link TODO software_type 对应的类}
     */
    private Integer appType;
    /**
     * 版本号
     */
    private String version;
    /**
     * 软件平台: 1=win, 3=macOS, 5=Linux
     *
     * 枚举 {@link TODO software_platform 对应的类}
     */
    private Integer platform;
    /**
     * 位版本：0=通用, 1=32位, 2=64位,
     *
     * 枚举 {@link TODO software_bit 对应的类}
     */
    private Integer bit;
    /**
     * 设备指纹
     */
    private String deviceFingerprint;
    /**
     * 系统版本
     */
    private String osRelease;
    /**
     * 描述
     */
    private String description;
    /**
     * 总更新次数
     */
    private Integer totalUpdateCount;
    /**
     * 今日更新次数
     */
    private Integer todayUpdateCount;


}