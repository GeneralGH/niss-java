package com.nanyang.academy.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author pt
 * @date 2022-06-02
 */
@Data
@ApiModel(value="{用户注册信息}", description="用户注册信息")
public class RegisterBody extends UserLoginDTO{
    @ApiModelProperty(value = "用户名称_登录名")
    @NotNull
    private String userName;
    @ApiModelProperty(value = "密码")
    @NotNull
    private String password;
    @ApiModelProperty(value = "验证码")
    private String code;
    @ApiModelProperty(value = "唯一标识")
    private String uuid;

    @ApiModelProperty(value = "用户类型")
    private String userType;
    @ApiModelProperty(value = "公司名称")
    private String company;

    @ApiModelProperty(value = "项目名称")
    private String project;

    @ApiModelProperty(value = "区域")
    private String area;

    @ApiModelProperty(value = "单位性质")
    private String property;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "微信号")
    private String wechatNo;
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
}
