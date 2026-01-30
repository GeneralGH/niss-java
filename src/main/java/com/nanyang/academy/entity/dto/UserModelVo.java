package com.nanyang.academy.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author pt
 * @date 2022-06-07
 */
@Data
@ApiModel(value="{用户列表展示信息}", description="用户列表展示信息")
public class UserModelVo {
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

    @ApiModelProperty(value = "公司名称")
    private String company;

    @ApiModelProperty(value = "用户状态")
    private Integer status;

    @ApiModelProperty(value = "区域名称")
    private String areaName;

    @ApiModelProperty(value = "接单状态")
    private Integer orderStatus;

    @ApiModelProperty(value = "在线状态")
    private Integer online;

    @ApiModelProperty(value = "是否接收消息推送  0不接受  1接受")
    private Integer isMessages;
    @ApiModelProperty(value = "项目名称")
    private String project;

    @ApiModelProperty(value = "是否开启声音提醒 0不开启  1开启")
    private Integer isRemind;

    @ApiModelProperty(value = "铃声URL")
    private String remindUrl;

    @ApiModelProperty(value = "备注名称")
    private String remarkName;

    @ApiModelProperty(value = "备注信息")
    private String remarkInfo;

    @ApiModelProperty(value = "单位性质")
    private String property;

    @ApiModelProperty(value = "自定义回复状态 0关闭  1开启")
    private String customStatus;

    @ApiModelProperty(value = "自定义回复内容")
    private String customReply;
}
