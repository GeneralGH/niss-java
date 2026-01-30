package com.nanyang.academy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nanyang.academy.annotation.Log;
import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.common.UserConstants;
import com.nanyang.academy.entity.SysDepartment;
import com.nanyang.academy.entity.param.DeptQueryParam;
import com.nanyang.academy.entity.enums.BusinessType;
import com.nanyang.academy.exception.CustomException;
import com.nanyang.academy.service.SysDepartmentService;
import com.nanyang.academy.utils.PageUtils;
import com.nanyang.academy.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sysDepartment")
@Api(tags = "部门")
public class SysDepartmentController extends BaseController {
    @Autowired
    private SysDepartmentService deptService;


    /**
     * 分页获取部门列表
     */
    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @ApiOperation(value = "分页获取部门列表")
    @GetMapping("/getDeptListPage")
    public ResultEntity getDeptListPage(@Validated DeptQueryParam dept)
    {
        IPage<SysDepartment> depts = deptService.selectDeptListPage(dept);
        return ResultEntity.getOkResult(new PageUtils<>(depts));
    }

    /**
     * 新增部门
     */
    @PreAuthorize("@ss.hasPermi('system:dept:add')")
    @ApiOperation(value = "新增部门")
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @PostMapping("/addDept")
    public ResultEntity add(@Validated @RequestBody SysDepartment dept)
    {
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept)))
        {
            throw new CustomException("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        dept.setCreateUser(getUsername());
        return ResultEntity.getOkResult(deptService.insertDept(dept));
    }

    /**
     * 修改部门
     */
    @PreAuthorize("@ss.hasPermi('system:dept:edit')")
    @ApiOperation(value = "修改部门")
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @PutMapping("/editDept")
    public ResultEntity edit(@Validated @RequestBody SysDepartment dept)
    {
        Long deptId = dept.getDeptId();
        deptService.checkDeptDataScope(deptId);
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept)))
        {
            throw new CustomException("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        else if (dept.getPId().equals(deptId))
        {
            throw new CustomException("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        }
        else if (StringUtil.equals(UserConstants.DEPT_DISABLE, dept.getStatus()) && deptService.selectNormalChildrenDeptById(deptId) > 0)
        {
            throw new CustomException("该部门包含未停用的子部门！");
        }
        dept.setUpdateUser(getUsername());
        return ResultEntity.getOkResult(deptService.updateDept(dept));
    }

    /**
     * 删除部门
     */
    @PreAuthorize("@ss.hasPermi('system:dept:remove')")
    @ApiOperation(value = "删除部门")
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @PutMapping("/remDept/{deptId}")
    public ResultEntity remove(@PathVariable Long deptId)
    {
        if (deptService.hasChildByDeptId(deptId))
        {
            throw new CustomException("存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId))
        {
            throw new CustomException("部门存在用户,不允许删除");
        }
        deptService.checkDeptDataScope(deptId);
        return ResultEntity.getOkResult(deptService.deleteDeptById(deptId));
    }

    /**
     * 根据部门编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:dept:query')")
    @ApiOperation(value = "根据部门编号获取详细信息")
    @GetMapping(value = "/getDeptInfo/{deptId}")
    public ResultEntity getInfo(@PathVariable Long deptId)
    {
        deptService.checkDeptDataScope(deptId);
        return ResultEntity.getOkResult(deptService.selectDeptById(deptId));
    }

    /**
     * 获取部门列表
     */
    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @ApiOperation(value = "获取部门列表")
    @GetMapping("/getDeptList")
    public ResultEntity getDeptList(SysDepartment dept)
    {
        List<SysDepartment> depts = deptService.selectDeptList(dept);
        return ResultEntity.getOkResult(depts);
    }
    /**
     * 加载对应角色部门列表树
     */
    @ApiOperation(value = "加载对应角色部门列表树")
    @GetMapping(value = "/roleDeptTreeselect/{roleId}")
    public ResultEntity roleDeptTreeselect(@PathVariable("roleId") Long roleId)
    {
        List<SysDepartment> depts = deptService.selectDeptList(new SysDepartment());
        Map ajax = new HashMap<>();
        ajax.put("checkedKeys", deptService.selectDeptListByRoleId(roleId));
        ajax.put("depts", deptService.buildDeptTreeSelect(depts));
        return ResultEntity.getOkResult(ajax);
    }

    /**
     * 获取部门下拉树列表
     */
    @ApiOperation(value = "获取部门下拉树列表")
    @GetMapping("/treeselect")
    public ResultEntity treeselect(SysDepartment dept)
    {
        List<SysDepartment> depts = deptService.selectDeptList(dept);
        return ResultEntity.getOkResult(deptService.buildDeptTreeSelect(depts));
    }

    /**
     * 查询部门列表（排除节点）
     */
    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @ApiOperation(value = "查询部门列表（排除节点）")
    @GetMapping("/list/exclude/{deptId}")
    public ResultEntity excludeChild(@PathVariable(value = "deptId", required = false) Long deptId)
    {
        List<SysDepartment> depts = deptService.selectDeptList(new SysDepartment());
        Iterator<SysDepartment> it = depts.iterator();
        while (it.hasNext())
        {
            SysDepartment d = (SysDepartment) it.next();
            if (d.getDeptId().intValue() == deptId
                    || ArrayUtils.contains(StringUtil.split(d.getAncestors(), ","), deptId + ""))
            {
                it.remove();
            }
        }
        return ResultEntity.getOkResult(depts);
    }
}
