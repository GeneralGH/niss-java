package com.nanyang.academy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nanyang.academy.annotation.Log;
import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.common.UserConstants;
import com.nanyang.academy.entity.SysRole;
import com.nanyang.academy.entity.SysRoleUser;
import com.nanyang.academy.entity.SysUser;
import com.nanyang.academy.entity.param.RoleQueryParam;
import com.nanyang.academy.entity.enums.BusinessType;
import com.nanyang.academy.entity.pojo.LoginUser;
import com.nanyang.academy.exception.CustomException;
import com.nanyang.academy.service.SysRoleService;
import com.nanyang.academy.service.SysUserService;
import com.nanyang.academy.service.impl.SysPermissionService;
import com.nanyang.academy.service.impl.TokenService;
import com.nanyang.academy.utils.PageUtils;
import com.nanyang.academy.utils.StringUtil;
import com.nanyang.academy.utils.file.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/sysRole")
@Api(tags = "角色")
public class SysRoleController extends BaseController {
    @Autowired
    private SysRoleService roleService;


    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private SysUserService userService;

    //@PreAuthorize("@ss.hasPermi('system:role:list')")
    @ApiOperation("分页获取角色")
    @GetMapping("/getRolelist")
    public ResultEntity list(@Validated RoleQueryParam role) {
        IPage<SysRole> list = roleService.selectRoleListPage(role);

        for (SysRole sysRole : list.getRecords()) {
            sysRole.setUserCount(roleService.getUserRoleCount(sysRole.getRoleId()));
        }

        return ResultEntity.getOkResult(new PageUtils<>(list));
    }

    //@Log(title = "角色管理", businessType = BusinessType.EXPORT)
    @ApiOperation("导出角色信息")
    //@PreAuthorize("@ss.hasPermi('system:role:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysRole role) {
        List<SysRole> list = roleService.selectRoleList(role);
        ExcelUtil<SysRole> util = new ExcelUtil<SysRole>(SysRole.class);
        util.exportExcel(response, list, "角色数据");
    }

    /**
     * 根据角色编号获取详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:role:query')")
    @ApiOperation("根据角色编号获取详细信息")
    @GetMapping(value = "/getRoleInfo/{roleId}")
    public ResultEntity getInfo(@PathVariable Long roleId) {
        //roleService.checkRoleDataScope(roleId);
        return ResultEntity.getOkResult(roleService.selectRoleById(roleId));
    }

    /**
     * 新增角色
     */
    //@PreAuthorize("@ss.hasPermi('system:role:add')")
    @ApiOperation("新增角色")
    //@Log(title = "角色管理", businessType = BusinessType.INSERT)
    @PostMapping("/addRole")
    public ResultEntity add(@Validated @RequestBody SysRole role) {
        if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
            throw new CustomException("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
            throw new CustomException("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setCreateUser(getUsername());
        return ResultEntity.getOkResult(roleService.insertRole(role));

    }

    /**
     * 修改保存角色
     */
    //@PreAuthorize("@ss.hasPermi('system:role:update')")
    @ApiOperation("修改保存角色")
    //@Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping("/editRole")
    public ResultEntity edit(@Validated @RequestBody SysRole role) {
        if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
            throw new CustomException("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
            throw new CustomException("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setUpdateUser(getUsername());

        if (roleService.updateRole(role) > 0) {
            // 更新缓存用户权限
            LoginUser loginUser = getLoginUser();
            if (StringUtil.isNotNull(loginUser.getUser()) && !loginUser.getUser().isAdmin()) {
                loginUser.setPermissions(permissionService.getMenuPermission(loginUser.getUser()));
                loginUser.setUser(userService.selectUserByUserName(loginUser.getUser().getUserName()));
                tokenService.setLoginUser(loginUser);
            }
            return ResultEntity.getOkResult();
        }
        return ResultEntity.getErrorResult("修改角色'" + role.getRoleName() + "'失败，请联系管理员");
    }

    /**
     * 修改保存数据权限
     */
    /*@PreAuthorize("@ss.hasPermi('system:role:update')")
    @ApiOperation("修改保存数据权限")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping("/dataScope")
    public ResultEntity dataScope(@RequestBody SysRole role) {
        roleService.checkRoleAllowed(role);
        roleService.checkRoleDataScope(role.getRoleId());
        return ResultEntity.getOkResult(roleService.authDataScope(role));
    }*/

    /**
     * 状态修改
     */
    //@PreAuthorize("@ss.hasPermi('system:role:update')")
    @ApiOperation("状态修改")
    //@Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public ResultEntity changeStatus(@RequestBody SysRole role) {
        roleService.checkRoleAllowed(role);
        roleService.checkRoleDataScope(role.getRoleId());
        role.setUpdateUser(getUsername());
        return toAjax(roleService.updateRoleStatus(role));
    }

    /**
     * 删除角色
     */
    //@PreAuthorize("@ss.hasPermi('system:role:delete')")
    @ApiOperation("删除角色")
    //@Log(title = "角色管理", businessType = BusinessType.DELETE)
    @PutMapping("/removeRole/{roleIds}")
    public ResultEntity remove(@PathVariable Long[] roleIds) {
        return ResultEntity.getOkResult(roleService.deleteRoleByIds(roleIds));
    }

    /**
     * 获取角色选择框列表
     */
    //@PreAuthorize("@ss.hasPermi('system:role:query')")
    @ApiOperation("获取角色选择框列表")
    @GetMapping("/optionselect")
    public ResultEntity optionselect() {
        return ResultEntity.getOkResult(roleService.selectRoleAll());
    }


    /**
     * 取消授权用户
     */
    //@PreAuthorize("@ss.hasPermi('system:role:update')")
    @ApiOperation("取消授权用户")
    //@Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/cancel")
    public ResultEntity cancelAuthUser(@RequestBody SysRoleUser userRole) {
        return ResultEntity.getOkResult(roleService.deleteAuthUser(userRole));
    }
}
