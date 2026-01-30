package com.nanyang.academy.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserPost implements Serializable {

	@ApiModelProperty(value = "用户ID")
	private Long userId;
	@ApiModelProperty(value = "岗位ID")
	private Long postId;
	
}
