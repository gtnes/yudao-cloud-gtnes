package cn.iocoder.yudao.module.infra.controller.admin.softwarerecord;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.infra.controller.admin.softwarerecord.vo.*;
import cn.iocoder.yudao.module.infra.dal.dataobject.softwarerecord.SoftwareRecordDO;
import cn.iocoder.yudao.module.infra.service.softwarerecord.SoftwareRecordService;

@Tag(name = "管理后台 - 软件使用记录")
@RestController
@RequestMapping("/infra/software-record")
@Validated
public class SoftwareRecordController {

    @Resource
    private SoftwareRecordService softwareRecordService;

    @PostMapping("/create")
    @Operation(summary = "创建软件使用记录")
    @PreAuthorize("@ss.hasPermission('infra:software-record:create')")
    public CommonResult<Long> createSoftwareRecord(@Valid @RequestBody SoftwareRecordSaveReqVO createReqVO) {
        return success(softwareRecordService.createSoftwareRecord(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新软件使用记录")
    @PreAuthorize("@ss.hasPermission('infra:software-record:update')")
    public CommonResult<Boolean> updateSoftwareRecord(@Valid @RequestBody SoftwareRecordSaveReqVO updateReqVO) {
        softwareRecordService.updateSoftwareRecord(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除软件使用记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('infra:software-record:delete')")
    public CommonResult<Boolean> deleteSoftwareRecord(@RequestParam("id") Long id) {
        softwareRecordService.deleteSoftwareRecord(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除软件使用记录")
                @PreAuthorize("@ss.hasPermission('infra:software-record:delete')")
    public CommonResult<Boolean> deleteSoftwareRecordList(@RequestParam("ids") List<Long> ids) {
        softwareRecordService.deleteSoftwareRecordListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得软件使用记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:software-record:query')")
    public CommonResult<SoftwareRecordRespVO> getSoftwareRecord(@RequestParam("id") Long id) {
        SoftwareRecordDO softwareRecord = softwareRecordService.getSoftwareRecord(id);
        return success(BeanUtils.toBean(softwareRecord, SoftwareRecordRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得软件使用记录分页")
    @PreAuthorize("@ss.hasPermission('infra:software-record:query')")
    public CommonResult<PageResult<SoftwareRecordRespVO>> getSoftwareRecordPage(@Valid SoftwareRecordPageReqVO pageReqVO) {
        PageResult<SoftwareRecordDO> pageResult = softwareRecordService.getSoftwareRecordPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SoftwareRecordRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出软件使用记录 Excel")
    @PreAuthorize("@ss.hasPermission('infra:software-record:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSoftwareRecordExcel(@Valid SoftwareRecordPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SoftwareRecordDO> list = softwareRecordService.getSoftwareRecordPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "软件使用记录.xls", "数据", SoftwareRecordRespVO.class,
                        BeanUtils.toBean(list, SoftwareRecordRespVO.class));
    }

    /**
     * 获取软件使用记录统计信息
     * <p>
     * 根据软件类别统计相关数据：
     * <ul>
     *   <li>今日新增合计（创建日期为今天的数据有多少条）</li>
     *   <li>今日更新合计（更新日期为今天的数据有多少条）</li>
     *   <li>数据总数（统计没有被删除的数据条数）</li>
     *   <li>平台设备总数（软件平台windows、Linux、Macos分别有多少条数据）</li>
     * </ul>
     * <p>
     * 数据来源：infra_gtnes_software_record 表
     *
     * @param reqVO 请求参数，包含软件类别字段 appType
     * @return 统计数据，包含今日新增、今日更新、数据总数和各平台数量
     */
    @GetMapping("/statistics")
    @Operation(summary = "获取软件使用记录统计信息")
    @jakarta.annotation.security.PermitAll
    public CommonResult<SoftwareRecordStatisticsRespVO> getStatistics(@Valid SoftwareRecordStatisticsReqVO reqVO) {
        return success(softwareRecordService.getStatistics(reqVO));
    }

}