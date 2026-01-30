package com.nanyang.academy.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author pt
 * @date 2022-06-09
 */
@Data
@ApiModel(value = "{基础查询参数}", description = "基础查询参数")
public class QueryBaseParam {
    @ApiModelProperty(value = "每页记录数", required = true)
    @NotNull
    private Integer size;

    @ApiModelProperty(value = "页数", required = true)
    @NotNull
    private Integer page;

    @ApiModelProperty(value = "开始时间")
    private Date beginTime;
    @ApiModelProperty(value = "结束时间")
    private Date endTime;
}
