package com.nanyang.academy.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author pt
 * @date 2022-06-07
 */
@Data
@ApiModel(value="{用户查询}", description="用户列表查询参数")
public class UserQueryParam extends QueryBaseParam implements Serializable {
    @ApiModelProperty(value = "用户名称_登录名")
    private String userName;
    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "用户类型  00系统用户  01普通用户")//
    private String userType;

    @ApiModelProperty(value = "电话")
    private String phone;

}
