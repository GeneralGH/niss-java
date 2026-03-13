package com.nanyang.academy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@ApiModel(value="{角色信息}", description="角色信息")
public class SysRole implements Serializable {
	public SysRole(){}
	public SysRole(Long roleId){this.roleId = roleId;}

	@ApiModelProperty(value = "角色编号")
	@TableId
	private Long roleId;
	@ApiModelProperty(value = "角色名称")
	private String roleName;
	@ApiModelProperty(value = "级别")
	private Integer level;
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@ApiModelProperty(value = "创建人")
	private String createUser;
	@ApiModelProperty(value = "修改时间")
	private Date updateTime;
	@ApiModelProperty(value = "修改人")
	private String updateUser;
	@ApiModelProperty(value = "是否删除 0否 1是")
	private Integer isDelete;
	@ApiModelProperty(value = "菜单状态0正常 1 锁定")
	private Integer status;
	@ApiModelProperty(value = "备注")
	private String remark;
	@ApiModelProperty(value = "角色权限字符串")
	private String roleKey;

	private Date beginTime;

	private Date endTime;

	@ApiModelProperty(value = "人数")
	private Integer userCount;

	@ApiModelProperty(value = "排序")
	private Integer roleSort;

	@ApiModelProperty(value = "数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）")
	private String dataScope;

	@ApiModelProperty(value = "菜单")
	private List<Map<String,Object>> menus;



	/** 用户是否存在此角色标识 默认不存在 */
	private boolean flag = false;


	/** 菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示） */
	private boolean menuCheckStrictly;

	/** 部门树选择项是否关联显示（0：父子不互相关联显示 1：父子互相关联显示 ） */
	private boolean deptCheckStrictly;

	/** 菜单组 */
	@ApiModelProperty(value = "菜单")
	private Long[] menuIds;
	/** 部门组（数据权限） */
	private Long[] deptIds;

	public boolean isDeptCheckStrictly()
	{
		return deptCheckStrictly;
	}
	public void setDeptCheckStrictly(boolean deptCheckStrictly)
	{
		this.deptCheckStrictly = deptCheckStrictly;
	}

	public boolean isAdmin()
	{
		return isAdmin(this.roleId);
	}

	public static boolean isAdmin(Long roleId)
	{
		return roleId != null && 1L == roleId;
	}


	public boolean isMenuCheckStrictly()
	{
		return menuCheckStrictly;
	}

	public void setMenuCheckStrictly(boolean menuCheckStrictly)
	{
		this.menuCheckStrictly = menuCheckStrictly;
	}

}
