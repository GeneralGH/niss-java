package com.nanyang.academy.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="{NewsQueryParam}", description="News查询参数")
public class NewsQueryParam extends QueryBaseParam implements Serializable {
    @ApiModelProperty(value = "1学院新闻  2校友动态 3加入他们")
    private Integer type;
    @ApiModelProperty(value = "语言")
    private Integer language;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "作者")
    private String author;
    @ApiModelProperty(value = "推荐")
    private Integer highlight;
    @ApiModelProperty(value = "校友动态类型")
    private Integer alumniType;
}
