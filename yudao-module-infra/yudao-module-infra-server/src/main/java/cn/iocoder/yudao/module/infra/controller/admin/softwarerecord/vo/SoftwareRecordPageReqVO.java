package cn.iocoder.yudao.module.infra.controller.admin.softwarerecord.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 软件使用记录分页 Request VO")
@Data
public class SoftwareRecordPageReqVO extends PageParam {

    @Schema(description = "ip", example = "162.1.1.2")
    private String ip;

    @Schema(description = "软件类别 1=9GridTools", example = "1")
    private Integer appType;

    @Schema(description = "版本号", example = "1.0.1")
    private String version;

    @Schema(description = "软件平台: 1=win, 3=macOS, 5=Linux", example = "1")
    private Integer platform;

    @Schema(description = "位版本：0=通用, 1=32位, 2=64位,", example = "0")
    private Integer bit;

    @Schema(description = "设备指纹")
    private String deviceFingerprint;

    @Schema(description = "系统版本", example = "10.2.53.3")
    private String osRelease;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "更新时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] updateTime;


}