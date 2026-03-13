package com.nanyang.academy.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="{TeacherQueryParam}", description="Teacher查询参数")
public class TeacherQueryParam extends QueryBaseParam implements Serializable {
    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "0普通  1精英")
    private String elite;
    @ApiModelProperty(value = "语言")
    private Integer language;
    @ApiModelProperty(value = "首字母")
    private String first;
}
