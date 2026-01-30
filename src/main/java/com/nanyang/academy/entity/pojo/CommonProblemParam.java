package com.nanyang.academy.entity.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author xj
 * @title: CommonProblemParam
 * @projectName gy
 * @description: TODO
 * @date 2022/7/18 9:07
 */
@Data
public class CommonProblemParam {

    @ApiModelProperty(value = "")
    private Long id;

    @ApiModelProperty(value = "标题")
    @NotEmpty(message = "请输入标题")
    private String title;

    @ApiModelProperty(value = "内容")
    @NotEmpty(message = "请输入内容")
    private String content;

}
