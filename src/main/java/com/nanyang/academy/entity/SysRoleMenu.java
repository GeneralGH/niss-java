package com.nanyang.academy.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysRoleMenu implements Serializable {

	@ApiModelProperty(value = "")
	private Long roleId;
	@ApiModelProperty(value = "")
	private Long menuId;

}
