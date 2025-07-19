package cn.iocoder.yudao.module.infra.controller.admin.softwarerecord.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 软件使用记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SoftwareRecordRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "ip", requiredMode = Schema.RequiredMode.REQUIRED, example = "162.1.1.2")
    @ExcelProperty("ip")
    private String ip;

    @Schema(description = "软件类别 1=9GridTools", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "软件类别 1=9GridTools", converter = DictConvert.class)
    @DictFormat("software_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer appType;

    @Schema(description = "版本号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.0.1")
    @ExcelProperty("版本号")
    private String version;

    @Schema(description = "软件平台: 1=win, 3=macOS, 5=Linux", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "软件平台: 1=win, 3=macOS, 5=Linux", converter = DictConvert.class)
    @DictFormat("software_platform") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer platform;

    @Schema(description = "位版本：0=通用, 1=32位, 2=64位,", example = "0")
    @ExcelProperty(value = "位版本：0=通用, 1=32位, 2=64位,", converter = DictConvert.class)
    @DictFormat("software_bit") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer bit;

    @Schema(description = "设备指纹", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("设备指纹")
    private String deviceFingerprint;

    @Schema(description = "系统版本", example = "10.2.53.3")
    @ExcelProperty("系统版本")
    private String osRelease;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("更新时间")
    private LocalDateTime updateTime;

}