package com.nanyang.academy.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName pt
 * @Description TODO
 * @Author pt
 * @Date 2022/8/24
 * @Version 1.0
 **/
@Data
@ApiModel(value = "{智能链信息}", description = "智能链信息")
public class SmartUser {
    @ApiModelProperty(value = "智能链登录账号")
    private String smartName;
    @ApiModelProperty(value = "智能链登录密码")
    private String smartPassword;
    @ApiModelProperty(value = "智能莲用户名称")
    private String smartRealName;
}
