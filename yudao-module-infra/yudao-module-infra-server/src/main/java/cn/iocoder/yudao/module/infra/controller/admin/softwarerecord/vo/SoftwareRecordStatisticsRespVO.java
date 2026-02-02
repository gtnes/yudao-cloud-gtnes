package cn.iocoder.yudao.module.infra.controller.admin.softwarerecord.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 软件使用记录统计响应VO
 *
 * @author gtnes
 */
@Data
public class SoftwareRecordStatisticsRespVO {

    /**
     * 今日新增合计（创建日期为今天的数据条数）
     */
    @Schema(description = "今日新增合计", example = "5")
    private Long todayNewCount;

    /**
     * 今日更新合计（更新日期为今天的数据条数）
     */
    @Schema(description = "今日更新合计", example = "10")
    private Long todayUpdateCount;

    /**
     * 数据总数（统计没有被删除的数据条数）
     */
    @Schema(description = "数据总数", example = "100")
    private Long totalCount;

    /**
     * Windows平台总数（软件平台为Windows的数据条数，platform=1）
     */
    @Schema(description = "Windows平台总数", example = "50")
    private Long windowsCount;

    /**
     * Linux平台总数（软件平台为Linux的数据条数，platform=5）
     */
    @Schema(description = "Linux平台总数", example = "30")
    private Long linuxCount;

    /**
     * MacOS平台总数（软件平台为MacOS的数据条数，platform=3）
     */
    @Schema(description = "MacOS平台总数", example = "20")
    private Long macOSCount;
}