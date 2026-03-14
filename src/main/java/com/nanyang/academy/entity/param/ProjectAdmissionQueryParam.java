package com.nanyang.academy.entity.param;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "ProjectAdmissionQueryParam", description = "项目与招生查询参数")
public class ProjectAdmissionQueryParam extends QueryBaseParam implements Serializable {
    @ApiModelProperty(value = "简介关键词")
    private String intro;
}