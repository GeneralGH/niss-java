package com.nanyang.academy.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author xj
 * @title: Business 商务合作
 * @projectName gy
 * @description: TODO
 * @date 2022/7/20 17:48
 */
@Data
public class BusinessParam {

    @ApiModelProperty(value = "姓名")
    @NotEmpty(message = "请输入姓名")
    private String username;

    @ApiModelProperty(value = "微信")
    @NotEmpty(message = "请输入微信号")
    private String wechat;

    @ApiModelProperty(value = "手机号")
    @NotEmpty(message = "请输入手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    @NotEmpty(message = "请输入邮箱")
    private String mail;

    @ApiModelProperty(value = "地址")
    @NotEmpty(message = "请输入地址")
    private String address;

    @ApiModelProperty(value = "详细地址")
    @NotEmpty(message = "请输入详细地址")
    private String addressDetailed;

    @ApiModelProperty(value = "经度")
    @NotEmpty(message = "经度不能为空")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    @NotEmpty(message = "纬度不能为空")
    private String latitude;


}
