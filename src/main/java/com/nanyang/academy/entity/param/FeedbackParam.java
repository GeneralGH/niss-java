package com.nanyang.academy.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName pt
 * @Description TODO
 * @Author pt
 * @Date 2023/7/21
 * @Version 1.0
 **/
@Data
@ApiModel(value = "FeedbackParam",description = "FeedbackParam")
public class FeedbackParam extends QueryBaseParam implements Serializable {
    @ApiModelProperty(value = "反馈知识库id")
    private Long knowlegeId;
    @ApiModelProperty(value = "知识库类型  1引导式知识库  2知识库")
    private Integer type;
    @ApiModelProperty(value = "反馈内容")
    private String content;
    @ApiModelProperty(value = "反馈原因")
    private Integer reason;
}
