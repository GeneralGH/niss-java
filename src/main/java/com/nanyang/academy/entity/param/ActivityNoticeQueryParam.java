package com.nanyang.academy.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 活动预告分页查询参数
 */
@Data
@ApiModel(value="{ActivityNoticeQueryParam}", description="ActivityNotice查询参数")
public class ActivityNoticeQueryParam implements Serializable {
    @ApiModelProperty(value = "标题")
    private String title;
}