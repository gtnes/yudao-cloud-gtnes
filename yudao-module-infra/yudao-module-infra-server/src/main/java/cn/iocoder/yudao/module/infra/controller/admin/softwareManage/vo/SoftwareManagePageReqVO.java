package cn.iocoder.yudao.module.infra.controller.admin.softwareManage.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 软件管理分页 Request VO")
@Data
public class SoftwareManagePageReqVO extends PageParam {

    @Schema(description = "软件名称", example = "9GridTools")
    private String name;

    @Schema(description = "版本号", example = "1.0.1")
    private String version;

    @Schema(description = "软件平台", example = "Windows")
    private Byte platform;

    @Schema(description = "位版本")
    private Byte bit;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}