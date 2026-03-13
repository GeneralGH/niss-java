package com.nanyang.academy.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author pt
 * @date 2022-06-07
 */
@Data
@ApiModel(value="{用户列表展示信息}", description="用户列表展示信息")
public class UserModelVo implements Serializable {
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "用户名称_登录名")
    private String userName;

    @ApiModelProperty(value = "角色名称")
    private String roleName;
    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "头像")
    private String avatar;
    @ApiModelProperty(value = "性别")
    private Integer sex;
    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "登录时间")
    private Date loginDate;

    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "登录IP")
    private String loginIp;


    @ApiModelProperty(value = "用户状态")
    private Integer status;
}
