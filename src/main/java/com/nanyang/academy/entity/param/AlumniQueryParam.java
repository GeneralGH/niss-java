package com.nanyang.academy.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="{AlumniQueryParam}", description="Alumni查询参数")
public class AlumniQueryParam extends QueryBaseParam implements Serializable {
    @ApiModelProperty(value = "语言")
    private Integer language;

    @ApiModelProperty(value = "姓名")
    private String name;
//    @ApiModelProperty(value = "姓名-英文")
//    private String nameEn;
//    @ApiModelProperty(value = "公司-英文")
//    private String companyEn;
//    @ApiModelProperty(value = "公司")
//    private String company;
}
