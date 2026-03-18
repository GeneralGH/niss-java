package com.nanyang.academy.entity.param;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class EnrollmentBrochureQueryParam extends QueryBaseParam implements Serializable {
    @ApiModelProperty(value = "标题")
    private String title;
}
