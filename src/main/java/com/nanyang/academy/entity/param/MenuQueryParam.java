package com.nanyang.academy.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author pt
 * @date 2022-06-09
 */
@Data
@ApiModel(value="{菜单查询}", description="菜单查询条件")
public class MenuQueryParam extends QueryBaseParam {
    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "菜单状态0正常 1 锁定")
    private Integer status;

    @ApiModelProperty(value = "用户id")
    private Long userId;


}
