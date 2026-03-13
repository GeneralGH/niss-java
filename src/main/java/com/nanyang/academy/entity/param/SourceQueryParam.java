package com.nanyang.academy.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="{SourceQueryParam}", description="SourceQueryParam")
public class SourceQueryParam extends QueryBaseParam implements Serializable {
    @ApiModelProperty(value = "网站名称")
    private String name;
}
