package com.nanyang.academy.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author pt
 * @date 2022-05-27
 */
@Data
@ApiModel(value="{用户登录信息}", description="用户登录信息")
public class UserLoginDTO implements Serializable {
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "验证码")
    private String code;
    @ApiModelProperty(value = "唯一标识")
    private String uuid;
}
