package com.nanyang.academy.entity.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author xj
 * @title: CompanyParam
 * @projectName gy
 * @description: TODO
 * @date 2022/7/21 9:06
 */
@Data
public class CompanyParam {

    @ApiModelProperty(value = "企业名称")
    @NotEmpty(message = "请输入企业名称")
    private String CompanyName;

    @ApiModelProperty(value = "企业图片")
    @NotEmpty(message = "请输入企业图片")
    private String CompanyImg;

    @ApiModelProperty(value = "企业内容")
    @NotEmpty(message = "请输入企业内容")
    private String CompanyContent;
}
