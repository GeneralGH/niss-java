package com.nanyang.academy.entity.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xj
 * @title: PageParam
 * @projectName gy
 * @description: TODO
 * @date 2022/7/14 16:52
 */
@Data
public class PageParam {

    @ApiModelProperty(value = "当前页数", required = true)
    @NotNull(message = "当前页数不为空")
    private Integer currentPage;

    @ApiModelProperty(value = "每页显示数量", required = true)
    @NotNull(message = "每页显示数量不为空")
    private Integer size;

}
