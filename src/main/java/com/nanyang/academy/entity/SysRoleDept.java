package com.nanyang.academy.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysRoleDept implements Serializable {

	@ApiModelProperty(value = "")
	private Long roleId;
	@ApiModelProperty(value = "")
	private Long deptId;

}
