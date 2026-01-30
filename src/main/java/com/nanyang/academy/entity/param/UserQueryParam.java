package com.nanyang.academy.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author pt
 * @date 2022-06-07
 */
@Data
@ApiModel(value="{用户查询}", description="用户列表查询参数")
public class UserQueryParam extends QueryBaseParam {
    @ApiModelProperty(value = "用户名称_登录名")
    private String userName;
    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "公司名称")
    private String company;

    @ApiModelProperty(value = "项目名称")
    private String project;

    @ApiModelProperty(value = "区域")
    private String area;

    @ApiModelProperty(value = "单位性质")
    private String property;

    @ApiModelProperty(value = "用户类型  00系统用户  01小程序用户")//
    private String userType;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "备注名称")
    private String remarkName;

    @ApiModelProperty(value = "备注信息")
    private String remarkInfo;

}
