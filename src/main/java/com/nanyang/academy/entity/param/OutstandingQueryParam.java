package com.nanyang.academy.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="{OutstandingQueryParam}", description="Outstanding查询参数")
public class OutstandingQueryParam extends QueryBaseParam implements Serializable {
    @ApiModelProperty(value = "语言")
    private Integer language;

    @ApiModelProperty(value = "姓名")
    private String name;
}
