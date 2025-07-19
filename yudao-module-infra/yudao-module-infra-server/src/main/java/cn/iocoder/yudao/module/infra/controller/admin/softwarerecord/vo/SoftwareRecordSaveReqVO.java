package cn.iocoder.yudao.module.infra.controller.admin.softwarerecord.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 软件使用记录新增/修改 Request VO")
@Data
public class SoftwareRecordSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "21555")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "ip", requiredMode = Schema.RequiredMode.REQUIRED, example = "162.1.1.2")
    @ExcelProperty("ip")
    private String ip;

    @Schema(description = "软件类别 1=9GridTools", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "软件类别 1=9GridTools不能为空")
    private Integer appType;

    @Schema(description = "版本号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.0.1")
    @NotEmpty(message = "版本号不能为空")
    private String version;

    @Schema(description = "软件平台: 1=win, 3=macOS, 5=Linux", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "软件平台: 1=win, 3=macOS, 5=Linux不能为空")
    private Integer platform;

    @Schema(description = "位版本：0=通用, 1=32位, 2=64位,", example = "0")
    private Integer bit;

    @Schema(description = "设备指纹", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "设备指纹不能为空")
    private String deviceFingerprint;

    @Schema(description = "系统版本", example = "10.2.53.3")
    private String osRelease;

    @Schema(description = "描述")
    private String description;

}