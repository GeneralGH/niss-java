package com.nanyang.academy.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author pt
 * @date 2022-06-10
 */
@Data
@ApiModel(value="{角色查询}", description="角色查询条件")
public class RoleQueryParam extends QueryBaseParam {
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色状态0正常 1 锁定")
    private Integer status;

    @ApiModelProperty(value = "角色名称")
    private String roleKey;
}
