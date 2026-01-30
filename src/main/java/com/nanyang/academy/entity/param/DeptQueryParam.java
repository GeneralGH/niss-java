package com.nanyang.academy.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author pt
 * @date 2022-06-10
 */
@Data
@ApiModel(value="{部门查询}", description="部门查询参数")
public class DeptQueryParam extends QueryBaseParam {

    @ApiModelProperty(value = "上级部门编号")
    private Long pId;
    @ApiModelProperty(value = "部门名称")
    private String deptName;
    @ApiModelProperty(value = "部门状态0正常 1 锁定")
    private Integer status;
}
