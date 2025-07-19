package cn.iocoder.yudao.module.infra.service.softwareManage;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.module.infra.controller.admin.softwareManage.vo.SoftwareManagePageReqVO;
import cn.iocoder.yudao.module.infra.controller.admin.softwareManage.vo.SoftwareManageSaveReqVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.iocoder.yudao.module.infra.dal.dataobject.softwareManage.SoftwareManageDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.infra.dal.mysql.softwareManage.SoftwareManageMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.infra.enums.ErrorCodeConstants.*;

/**
 * 软件管理 Service 实现类
 *
 * @author gtnes
 */
@Service
@Validated
public class SoftwareManageServiceImpl implements SoftwareManageService {

    @Resource
    private SoftwareManageMapper softwareManageMapper;

    @Override
    public Long createSoftwareManage(SoftwareManageSaveReqVO createReqVO) {
        // 插入
        SoftwareManageDO softwareManage = BeanUtils.toBean(createReqVO, SoftwareManageDO.class);
        softwareManageMapper.insert(softwareManage);
        // 返回
        return softwareManage.getId();
    }

    @Override
    public void updateSoftwareManage(SoftwareManageSaveReqVO updateReqVO) {
        // 校验存在
        validateSoftwareManageExists(updateReqVO.getId());
        // 更新
        SoftwareManageDO updateObj = BeanUtils.toBean(updateReqVO, SoftwareManageDO.class);
        softwareManageMapper.updateById(updateObj);
    }

    @Override
    public void deleteSoftwareManage(Long id) {
        // 校验存在
        validateSoftwareManageExists(id);
        // 删除
        softwareManageMapper.deleteById(id);
    }

    @Override
        public void deleteSoftwareManageListByIds(List<Long> ids) {
        // 校验存在
        validateSoftwareManageExists(ids);
        // 删除
        softwareManageMapper.deleteByIds(ids);
        }

    private void validateSoftwareManageExists(List<Long> ids) {
        List<SoftwareManageDO> list = softwareManageMapper.selectByIds(ids);
        if (CollUtil.isEmpty(list) || list.size() != ids.size()) {
            throw exception(SOFTWARE_MANAGE_NOT_EXISTS);
        }
    }

    private void validateSoftwareManageExists(Long id) {
        if (softwareManageMapper.selectById(id) == null) {
            throw exception(SOFTWARE_MANAGE_NOT_EXISTS);
        }
    }

    @Override
    public SoftwareManageDO getSoftwareManage(Long id) {
        return softwareManageMapper.selectById(id);
    }

    @Override
    public PageResult<SoftwareManageDO> getSoftwareManagePage(SoftwareManagePageReqVO pageReqVO) {
        return softwareManageMapper.selectPage(pageReqVO);
    }

    @Override
    public List<SoftwareManageDO> getNewVersions(String name, String version, Byte platform) {
        List<SoftwareManageDO> all = softwareManageMapper.selectListByNameAndPlatform(name, platform);
        List<SoftwareManageDO> result = new ArrayList<>();
        for (SoftwareManageDO item : all) {
            if (compareVersion(item.getVersion(), version) > 0) {
                result.add(item);
            }
        }
        // 按id倒序排列
        result.sort(Comparator.comparing(SoftwareManageDO::getId).reversed());
        return result;
    }

    /**
     * 版本号比较，返回1表示v1>v2，0表示相等，-1表示v1<v2
     */
    private int compareVersion(String v1, String v2) {
        if (v1 == null || v2 == null) return 0;
        String[] arr1 = v1.split("\\.");
        String[] arr2 = v2.split("\\.");
        int len = Math.max(arr1.length, arr2.length);
        for (int i = 0; i < len; i++) {
            int n1 = i < arr1.length ? Integer.parseInt(arr1[i]) : 0;
            int n2 = i < arr2.length ? Integer.parseInt(arr2[i]) : 0;
            if (n1 != n2) return n1 > n2 ? 1 : -1;
        }
        return 0;
    }
}