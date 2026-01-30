package com.nanyang.academy.entity.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xj
 * @title: InfoBannerListParam
 * @projectName gy
 * @description: TODO
 * @date 2022/7/15 14:02
 */
@Data
public class ListParam extends PageParam {

    @ApiModelProperty(value = "标题")
    private String title;

}
