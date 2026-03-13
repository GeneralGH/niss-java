package com.nanyang.academy.entity.param;

import com.nanyang.academy.entity.PageConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="{PageConfigQueryParam}", description="页面设置查询参数")
public class PageConfigQueryParam extends QueryBaseParam implements Serializable {
    @ApiModelProperty(value = "位置")
    private String position;
    @ApiModelProperty(value = "语言")
    private Integer language;
}
