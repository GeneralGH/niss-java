package com.nanyang.academy.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="{CustomerQueryParam}", description="Customer查询参数")
public class CustomerQueryParam extends QueryBaseParam implements Serializable {
    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "语言")
    private Integer language;

    @ApiModelProperty(value = "咨询项目")
    private String project;

}
