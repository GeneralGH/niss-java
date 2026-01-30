package com.nanyang.academy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.nanyang.academy.annotation.Excel;
import com.nanyang.academy.annotation.Excels;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(value="{用户信息}", description="用户信息")
public class SysUser implements Serializable {
	public SysUser(){}

	public SysUser(Long userId)
	{
		this.userId = userId;
	}

	@ApiModelProperty(value = "用户id")
	@TableId(value = "user_id", type = IdType.AUTO)
	private Long userId;
	@ApiModelProperty(value = "用户名称_登录名")
	private String userName;
	@ApiModelProperty(value = "用户昵称")
	private String nickName;
	@ApiModelProperty(value = "用户身份证号")
	private String userIdentityCard;
	@ApiModelProperty(value = "密码")
	private String password;
	@ApiModelProperty(value = "部门编号")
	private Long deptId;
	@ApiModelProperty(value = "部门名称")
	private String deptName;
	@ApiModelProperty(value = "头像")
	private String avatar;
	@ApiModelProperty(value = "性别")
	private Integer sex;
	@ApiModelProperty(value = "电话")
	private String phone;
	@ApiModelProperty(value = "住址")
	private String address;
	@ApiModelProperty(value = "生日")
	private Date birthday;
	@ApiModelProperty(value = "职务")
	private Integer postId;
	@ApiModelProperty(value = "验证码")
	private String code;
	@ApiModelProperty(value = "微信号")
	private String wechatNo;
	@ApiModelProperty(value = "")
	private String openid;
	@ApiModelProperty(value = "")
	private String token;
	@ApiModelProperty(value = "创建日期")
	private Date createTime;
	@ApiModelProperty(value = "创建人")
	private String createUser;
	@ApiModelProperty(value = "最后一次修改日期")
	private Date updateTime;
	@ApiModelProperty(value = "修改人")
	private String updateUser;
	@ApiModelProperty(value = "是否删除 0否 1是")
	private Integer isDelete;
	@ApiModelProperty(value = "邮箱")
	private String email;
	@ApiModelProperty(value = "备注")
	private String remark;
	@ApiModelProperty(value = "登录IP")
	private String loginIp;
	@ApiModelProperty(value = "登录时间")
	private Date loginDate;

	@ApiModelProperty(value = "公司名称")
	private String company;

	@ApiModelProperty(value = "项目名称")
	private String project;

	@ApiModelProperty(value = "区域")
	private String area;

	@ApiModelProperty(value = "区域名称")
	private String areaName;

	@ApiModelProperty(value = "单位性质")
	private String property;

	@ApiModelProperty(value = "智能链登录账号")
	private String smartName;
	@ApiModelProperty(value = "智能链密码")
	private String smartPassword;

	@ApiModelProperty(value = "智能莲用户名称")
	private String smartRealName;

	@ApiModelProperty(value = "智能莲绑定状态")
	private Integer isBindSmart;


	@ApiModelProperty(value = "用户状态")
	private Integer status;

	@ApiModelProperty(value = "用户类型  00系统用户  01普通用户")//00系统用户  01普通用户
	private String userType;


	/** 部门对象 */
	@Excels({
			@Excel(name = "部门名称", targetAttr = "deptName", type = Excel.Type.EXPORT),
			@Excel(name = "部门负责人", targetAttr = "leader", type = Excel.Type.EXPORT)
	})
	private SysDepartment dept;

	/** 角色对象 */
	private List<SysRole> roles;

	/** 角色组 */
	private Long[] roleIds;

	/** 岗位组 */
	private Long[] postIds;


	public boolean isAdmin()
	{
		return isAdmin(this.userId);
	}

	public static boolean isAdmin(Long userId)
	{
		return userId != null && 1L == userId;
	}


}
