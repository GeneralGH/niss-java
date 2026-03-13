package com.nanyang.academy.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="{PromotionQueryParam}", description="PromotionQueryParam查询参数")
public class PromotionQueryParam extends QueryBaseParam implements Serializable {
    @ApiModelProperty(value = "语言")
    private Integer language;

    @ApiModelProperty(value = "标题")
    private String title;
}
