package com.nanyang.academy.entity.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xj
 * @title: TitleVo
 * @projectName gy
 * @description: TODO
 * @date 2022/7/20 16:12
 */
@Data
public class TitleVo {

    @ApiModelProperty(value = "")
    private Integer id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "一级标签id")
    private Integer priLabelId;

    @ApiModelProperty(value = "二级标签id")
    private Integer secLabelId;



}
