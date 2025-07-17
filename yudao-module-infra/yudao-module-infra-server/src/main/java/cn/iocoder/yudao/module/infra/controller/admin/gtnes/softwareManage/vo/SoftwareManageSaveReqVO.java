package cn.iocoder.yudao.module.infra.controller.admin.gtnes.softwareManage.vo;

import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 软件管理新增/修改 Request VO")
@Data
public class SoftwareManageSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "21555")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "软件名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "9GridTools")
    @NotEmpty(message = "软件名称不能为空")
    private String name;

    @Schema(description = "版本号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.0.1")
    @NotEmpty(message = "版本号不能为空")
    private String version;

    @Schema(description = "软件平台", requiredMode = Schema.RequiredMode.REQUIRED, example = "Windows")
    @NotNull(message = "软件平台不能为空")
    private Byte platform;

    @Schema(description = "位版本")
    @ExcelProperty(value = "软件版本", converter = DictConvert.class)
    private Byte bit;


    @Schema(description = "简介", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "简介不能为空")
    private String description;

    @Schema(description = "下载地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "http://www.url.com")
    @NotEmpty(message = "下载地址不能为空")
    private String downloadUrl;

}