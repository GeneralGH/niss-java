package com.nanyang.academy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "{菜单信息}", description = "菜单信息")
public class SysMenu implements Serializable {

    @ApiModelProperty(value = "菜单id")
    @TableId
    private Long menuId;
    @ApiModelProperty(value = "菜单名称")
    private String menuName;
    
    private String vName;
    @ApiModelProperty(value = "访问路径")
    private String menuUrl;
    @ApiModelProperty(value = "菜单图标")
    private String icon;
    @ApiModelProperty(value = "上级菜单ID，一级菜单为-1")
    private Long pid;
    @ApiModelProperty(value = "菜单级别")
    private Integer menuLevel;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "创建人")
    private String createUser;
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    @ApiModelProperty(value = "修改人")
    private String updateUser;
    @ApiModelProperty(value = "对否删除 0否 1是")
    private Integer isDelete;
    @ApiModelProperty(value = "菜单权限标识")
    private String perms;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "菜单类型（M目录 C菜单 F按钮）")
    private String menuType;
    @ApiModelProperty(value = "是否为外链（0是 1否）")
    private Integer isFrame;
    @ApiModelProperty(value = "组件路径")
    private String component;

    @ApiModelProperty(value = "路由参数")
    private String query;

    @ApiModelProperty(value = "是否缓存")
    private String isCache;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "是否隐藏 0false显示 1true隐藏 ")
    private Boolean hidden;


    private List<SysMenu> children = new ArrayList<SysMenu>();


}
