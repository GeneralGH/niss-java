package com.nanyang.academy.entity.dto;

import com.nanyang.academy.entity.SysMenu;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName pt
 * @Description TODO
 * @Author pt
 * @Date 2022/7/20
 * @Version 1.0
 **/
@Data
public class PermsSelect implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private Long key;

    /** 节点名称 */
    private String title;

    private String icon;

    private String url;

    private String component;

    /** 菜单权限标识 */
    private String perms;

    public PermsSelect()
    {

    }

    public PermsSelect(SysMenu menu)
    {
        this.key = menu.getMenuId();
        this.title = menu.getMenuName();
        this.component = menu.getComponent();
        this.url = menu.getMenuUrl();
        this.perms = menu.getPerms();
        this.icon = menu.getIcon();
    }
}
