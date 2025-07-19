package cn.iocoder.yudao.module.infra.controller.admin.softwareManage.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 软件管理 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SoftwareManageRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "软件名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "9GridTools")
    @ExcelProperty("软件名称")
    private String name;

    @Schema(description = "版本号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.0.1")
    @ExcelProperty("版本号")
    private String version;

    @Schema(description = "软件平台", requiredMode = Schema.RequiredMode.REQUIRED, example = "Windows")
    @ExcelProperty(value = "软件平台", converter = DictConvert.class)
    @DictFormat("software_platform") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Byte platform;

    @Schema(description = "位版本")
    @ExcelProperty(value = "软件版本", converter = DictConvert.class)
    @DictFormat("software_bit") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Byte bit;

    @Schema(description = "简介", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "简介不能为空")
    private String description;

    @Schema(description = "下载地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "http://www.url.com")
    @NotEmpty(message = "下载地址不能为空")
    private String downloadUrl;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;
}