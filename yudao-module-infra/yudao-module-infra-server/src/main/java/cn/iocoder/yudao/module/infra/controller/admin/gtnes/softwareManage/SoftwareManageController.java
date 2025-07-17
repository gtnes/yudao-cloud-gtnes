package cn.iocoder.yudao.module.infra.controller.admin.gtnes.softwareManage;

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

import cn.iocoder.yudao.module.infra.controller.admin.gtnes.softwareManage.vo.*;
import cn.iocoder.yudao.module.infra.dal.dataobject.gtnes.softwareManage.SoftwareManageDO;
import cn.iocoder.yudao.module.infra.service.gtnes.softwareManage.SoftwareManageService;

@Tag(name = "管理后台 - 软件管理")
@RestController
@RequestMapping("/infra/software-manage")
@Validated
public class SoftwareManageController {

    @Resource
    private SoftwareManageService softwareManageService;

    @PostMapping("/create")
    @Operation(summary = "创建软件管理")
    @PreAuthorize("@ss.hasPermission('infra:software-manage:create')")
    public CommonResult<Long> createSoftwareManage(@Valid @RequestBody SoftwareManageSaveReqVO createReqVO) {
        return success(softwareManageService.createSoftwareManage(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新软件管理")
    @PreAuthorize("@ss.hasPermission('infra:software-manage:update')")
    public CommonResult<Boolean> updateSoftwareManage(@Valid @RequestBody SoftwareManageSaveReqVO updateReqVO) {
        softwareManageService.updateSoftwareManage(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除软件管理")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('infra:software-manage:delete')")
    public CommonResult<Boolean> deleteSoftwareManage(@RequestParam("id") Long id) {
        softwareManageService.deleteSoftwareManage(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除软件管理")
                @PreAuthorize("@ss.hasPermission('infra:software-manage:delete')")
    public CommonResult<Boolean> deleteSoftwareManageList(@RequestParam("ids") List<Long> ids) {
        softwareManageService.deleteSoftwareManageListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得软件管理")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:software-manage:query')")
    public CommonResult<SoftwareManageRespVO> getSoftwareManage(@RequestParam("id") Long id) {
        SoftwareManageDO softwareManage = softwareManageService.getSoftwareManage(id);
        return success(BeanUtils.toBean(softwareManage, SoftwareManageRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得软件管理分页")
    @PreAuthorize("@ss.hasPermission('infra:software-manage:query')")
    public CommonResult<PageResult<SoftwareManageRespVO>> getSoftwareManagePage(@Valid SoftwareManagePageReqVO pageReqVO) {
        PageResult<SoftwareManageDO> pageResult = softwareManageService.getSoftwareManagePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SoftwareManageRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出软件管理 Excel")
    @PreAuthorize("@ss.hasPermission('infra:software-manage:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSoftwareManageExcel(@Valid SoftwareManagePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SoftwareManageDO> list = softwareManageService.getSoftwareManagePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "软件管理.xls", "数据", SoftwareManageRespVO.class,
                        BeanUtils.toBean(list, SoftwareManageRespVO.class));
    }

    @PostMapping("/check-new-ver")
    @Operation(summary = "检查是否有新版本")
    @jakarta.annotation.security.PermitAll
    public CommonResult<List<SoftwareManageRespVO>> checkNewVersion(@RequestParam("name") String name,
                                                                   @RequestParam("version") String version,
                                                                   @RequestParam("platform") Byte platform) {
        List<SoftwareManageDO> newVersions = softwareManageService.getNewVersions(name, version, platform);
        return success(BeanUtils.toBean(newVersions, SoftwareManageRespVO.class));
    }

}