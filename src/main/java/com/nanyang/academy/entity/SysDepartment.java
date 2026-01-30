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
@ApiModel(value="{部门信息}", description="部门信息")
public class SysDepartment implements Serializable {

	@ApiModelProperty(value = "部门编号")
	@TableId
	private Long deptId;
	@ApiModelProperty(value = "部门名称")
	private String deptName;
	@ApiModelProperty(value = "部门级别")
	private Integer deptLevel;
	@ApiModelProperty(value = "上级部门  一级为-1")
	private Long pId;
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

	@ApiModelProperty(value = "领导者")
	private String leader;

	@ApiModelProperty(value = "部门状态 0正常 1停用")
	private String status;
	@ApiModelProperty(value = "祖级列表")
	private String ancestors;

	@ApiModelProperty(value = "部门电话")
	private String phone;
	@ApiModelProperty(value = "邮箱")
	private String email;
	@ApiModelProperty(value = "上级机构名称")
	private String pName;

	private List<SysDepartment> children = new ArrayList<SysDepartment>();

}
