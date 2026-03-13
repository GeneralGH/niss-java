package com.nanyang.academy.entity.param;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="{CourseQueryParam}", description="CourseQueryParam查询参数")
public class CourseQueryParam extends QueryBaseParam implements Serializable {
    @ApiModelProperty(value = "1必修  2选修")
    private Integer type;
    @ApiModelProperty(value = "标题")
    private String title;
}
