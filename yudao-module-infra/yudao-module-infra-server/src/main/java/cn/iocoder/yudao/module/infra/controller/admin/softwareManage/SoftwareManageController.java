package cn.iocoder.yudao.module.infra.controller.admin.softwareManage;

import cn.iocoder.yudao.module.infra.controller.admin.softwareManage.vo.SoftwareManagePageReqVO;
import cn.iocoder.yudao.module.infra.controller.admin.softwareManage.vo.SoftwareManageRespVO;
import cn.iocoder.yudao.module.infra.controller.admin.softwareManage.vo.SoftwareManageSaveReqVO;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

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

import cn.iocoder.yudao.module.infra.controller.admin.softwareManage.vo.*;
import cn.iocoder.yudao.module.infra.dal.dataobject.softwareManage.SoftwareManageDO;
import cn.iocoder.yudao.module.infra.service.softwareManage.SoftwareManageService;
import cn.iocoder.yudao.module.infra.controller.admin.softwarerecord.vo.SoftwareRecordSaveReqVO;
import cn.iocoder.yudao.module.infra.service.softwarerecord.SoftwareRecordService;

@Tag(name = "管理后台 - 软件管理")
@RestController
@RequestMapping("/infra/software-manage")
@Validated
public class SoftwareManageController {

    @Resource
    private SoftwareManageService softwareManageService;

    @Resource
    private SoftwareRecordService softwareRecordService;

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

    /**
     * 检查是否有新版本，并记录软件使用信息
     * <p>
     * 前端通过JSON body传递所有参数，包括：
     * <ul>
     *   <li>name：软件名称</li>
     *   <li>platform：软件平台编号（如1=win, 3=macOS, 5=Linux）</li>
     *   <li>appVersion：当前软件版本号</li>
     *   <li>deviceFingerprint：设备指纹</li>
     *   <li>platformName：平台名称</li>
     *   <li>osRelease：操作系统版本</li>
     *   <li>osArch：操作系统架构</li>
     *   <li>appType：应用类型</li>
     * </ul>
     * 方法会先根据name、platform、appVersion查询新版本列表，再记录软件使用信息。
     *
     * @param reqVO 前端传递的参数对象
     * @param request HttpServletRequest，用于获取客户端IP
     * @return 新版本软件列表
     */
    @PostMapping("/check-new-ver")
    @Operation(summary = "检查是否有新版本并记录软件使用信息")
    @jakarta.annotation.security.PermitAll
    public CommonResult<List<SoftwareManageRespVO>> checkNewVersion(@RequestBody CheckNewVerReqVO reqVO, HttpServletRequest request) {
        // 查询新版本列表，根据软件名称、平台编号和当前版本号
        List<SoftwareManageDO> newVersions = softwareManageService.getNewVersions(
            reqVO.getName(),
            reqVO.getAppVersion(),
            reqVO.getPlatform() == null ? null : reqVO.getPlatform().byteValue()
        );
        // 获取客户端IP地址
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 转换操作系统架构为数据库bit字段
        Integer bit = convertBit(reqVO.getOsArch());
        // 记录或更新软件使用信息（根据设备指纹和IP）
        softwareRecordService.createOrUpdateByFingerprintAndIp(
            reqVO.getDeviceFingerprint(), // 设备指纹
            ip, // 客户端IP
            reqVO.getPlatform(), // 平台编号
            bit, // 操作系统架构
            reqVO.getPlatformName(), // 平台名称
            reqVO.getOsRelease(), // 操作系统版本
            reqVO.getAppType(), // 应用类型
            reqVO.getAppVersion() // 应用版本
        );
        // 返回新版本软件列表
        return success(BeanUtils.toBean(newVersions, SoftwareManageRespVO.class));
    }

    private Integer convertBit(String osArch) {
        if ("x64".equalsIgnoreCase(osArch)) return 2;
        if ("ia32".equalsIgnoreCase(osArch)) return 1;
        return 0;
    }
}