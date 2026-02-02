package cn.iocoder.yudao.module.infra.controller.admin.softwarerecord.vo;

import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * 软件使用记录统计请求VO
 *
 * @author gtnes
 */
public class SoftwareRecordStatisticsReqVO {

    /**
     * 软件类别（1=9GridTools）
     * 枚举 {@link cn.iocoder.yudao.module.infra.enums.SoftwareTypeEnum 对应的枚举类}
     */
    @NotNull(message = "软件类别不能为空")
    @Parameter(description = "软件类别", required = true, example = "1")
    private Integer appType;

    public Integer getAppType() { return appType; }
    public void setAppType(Integer appType) { this.appType = appType; }
}