package com.nanyang.academy.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanyang.academy.entity.SysMenu;
import com.nanyang.academy.entity.param.MenuQueryParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author HengYuanKeJi
 */
@Component
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> selectMenuTreeByUserId(Long userId);

    List<String> selectMenuPermsByUserId(Long userId);

    List<SysMenu> selectMenuList(MenuQueryParam param);

    IPage<SysMenu> selectMenuListPlus(Page<SysMenu> page, @Param(Constants.WRAPPER) QueryWrapper<MenuQueryParam> wrapper);

    List<SysMenu> selectMenuListByUserId(MenuQueryParam param);

    List<SysMenu> selectMenuTreeAll();

    List<Long> selectMenuListByRoleId(Long roleId, Object menuCheckStrictly);

    SysMenu selectMenuById(Long menuId);

    int hasChildByMenuId(Long menuId);

    int updateMenu(SysMenu menu);

    int deleteMenuById(Long menuId);

    SysMenu checkMenuNameUnique(String menuName, Long pid);

    List<SysMenu> getFPerms(Long userId, Long menuId);

    List<Map<String,Object>> selectMenuListSelect();

    List<Map<String, Object>> selectMenuByRoleId(Long roleId);
}
