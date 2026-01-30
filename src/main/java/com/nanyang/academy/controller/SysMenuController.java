package com.nanyang.academy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nanyang.academy.annotation.Log;
import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.common.UserConstants;
import com.nanyang.academy.entity.SysMenu;
import com.nanyang.academy.entity.param.MenuQueryParam;
import com.nanyang.academy.entity.enums.BusinessType;
import com.nanyang.academy.exception.CustomException;
import com.nanyang.academy.service.SysMenuService;
import com.nanyang.academy.service.impl.SysPermissionService;
import com.nanyang.academy.utils.PageUtils;
import com.nanyang.academy.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sysMenu")
@Api(tags = "菜单")
public class SysMenuController extends BaseController {
    @Autowired
    private SysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 分页查询菜单列表
     */
    @PreAuthorize("@ss.hasPermi('system:menu:list')")
    @ApiOperation("分页查询菜单列表")
    @GetMapping("/getMenulistPage")
    public ResultEntity getMenulistPage(@Validated MenuQueryParam param)
    {
        param.setUserId(getUserId());
        IPage<SysMenu> menus = menuService.selectMenuListPlus(param);
        return ResultEntity.getOkResult(new PageUtils(menus));
    }

    /**
     * 获取菜单列表
     */
    @PreAuthorize("@ss.hasPermi('system:menu:list')")
    @ApiOperation("获取菜单列表")
    @GetMapping("/getMenulist")
    public ResultEntity list(MenuQueryParam param)
    {
        param.setUserId(getUserId());
        List<SysMenu> menus = menuService.selectMenuList(param);
        return ResultEntity.getOkResult(menus);
    }


    /**
     * 获取菜单列表
     */
    @ApiOperation("获取所有菜单名称")
    @GetMapping("/getMenulistSelect")
    public ResultEntity getMenulistSelect()
    {

        List<Map<String,Object>> menus = menuService.selectMenuListSelect();
        return ResultEntity.getOkResult(menus);
    }


    /**
     * 根据菜单编号获取详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:menu:query')")
    @ApiOperation("根据菜单编号获取详细信息")
    @GetMapping(value = "/getMenuInfo/{menuId}")
    public ResultEntity getInfo(@PathVariable Long menuId)
    {
        return ResultEntity.getOkResult(menuService.selectMenuById(menuId));
    }

    /**
     * 根据菜单编号获取当前用户按钮权限
     */
    @ApiOperation("根据菜单编号获取当前用户按钮权限")
    @GetMapping(value = "/getFPerms/{menuId}")
    public ResultEntity getFPerms(@PathVariable Long menuId)
    {
        Long userId  = getUserId();
        return ResultEntity.getOkResult(menuService.getFPerms(userId,menuId));
    }

    @ApiOperation("获取当前用户perms")
    @GetMapping(value = "/getPerms")
    public ResultEntity getPerms()
    {
        return ResultEntity.getOkResult(permissionService.getMenuPermission(getLoginUser().getUser()));
    }

    /**
     * 获取菜单下拉树列表
     */
    @ApiOperation("获取菜单下拉树列表")
    @GetMapping("/treeselect")
    public ResultEntity treeselect()
    {
        List<SysMenu> menus = menuService.selectMenuList(getUserId());
        return ResultEntity.getOkResult(menuService.buildMenuTreeSelect(menus));
    }

    /**
     * 加载对应角色菜单列表树
     */
    //@ApiOperation("加载对应角色菜单列表树")
    //@GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public ResultEntity roleMenuTreeselect(@PathVariable("roleId") Long roleId)
    {
        List<SysMenu> menus = menuService.selectMenuList(getUserId());
        ResultEntity ajax = ResultEntity.getOkResult();
        Map res = new HashMap();
        res.put("checkedKeys", menuService.selectMenuListByRoleId(roleId));
        res.put("menus", menuService.buildMenuTreeSelect(menus));
        ajax.setData(res);
        return ajax;
    }

    /**
     * 加载当前用户菜单权限
     */
    //@ApiOperation("获取当前用户路由信息")
    @GetMapping(value = "/selectMenuPermsByUserId")
    public ResultEntity selectMenuPermsByUserId()
    {
        Long userId = getLoginUser().getUserId();
        List<SysMenu> menus = menuService.selectMenuList(userId);
        Map data = new HashMap();
        data.put("menus",menuService.buildPermsSelect(menus));
        return ResultEntity.getOkResult(data);
    }

    /**
     * 新增菜单
     */
    @ApiOperation("新增菜单")
    @PreAuthorize("@ss.hasPermi('system:menu:add')")
    @Log(title = "菜单管理", businessType = BusinessType.INSERT)
    @PostMapping("/addMenu")
    public ResultEntity add(@Validated @RequestBody SysMenu menu)
    {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu)))
        {
            throw new CustomException("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }
        else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtil.ishttp(menu.getMenuUrl()))
        {
            throw new CustomException("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        menu.setCreateUser(getUsername());
        return ResultEntity.getOkResult(menuService.insertMenu(menu));
    }

    /**
     * 修改菜单
     */
    @ApiOperation("修改菜单")
    @PreAuthorize("@ss.hasPermi('system:menu:edit')")
    @Log(title = "菜单管理", businessType = BusinessType.UPDATE)
    @PutMapping("/editMenu")
    public ResultEntity edit(@Validated @RequestBody SysMenu menu)
    {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu)))
        {
            throw new CustomException("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }
        else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtil.ishttp(menu.getMenuUrl()))
        {
            throw new CustomException("修改菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        else if (menu.getMenuId().equals(menu.getPid()))
        {
            throw new CustomException("修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        menu.setUpdateUser(getUsername());
        return ResultEntity.getOkResult(menuService.updateMenu(menu));
    }

    /**
     * 删除菜单
     */
    @ApiOperation("删除菜单")
    @PreAuthorize("@ss.hasPermi('system:menu:remove')")
    @Log(title = "菜单管理", businessType = BusinessType.DELETE)
    @PutMapping("/removeMenu")
    public ResultEntity remove(@RequestParam("menuId") Long menuId)
    {
        if (menuService.hasChildByMenuId(menuId))
        {
            throw new CustomException("存在子菜单,不允许删除");
        }
        if (menuService.checkMenuExistRole(menuId))
        {
            throw new CustomException("菜单已分配,不允许删除");
        }
        return ResultEntity.getOkResult(menuService.deleteMenuById(menuId));
    }
}
