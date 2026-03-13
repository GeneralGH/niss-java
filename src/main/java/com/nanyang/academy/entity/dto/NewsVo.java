package com.nanyang.academy.entity.dto;

import com.nanyang.academy.entity.News;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="{NewsVo}", description="NewsVo信息")
public class NewsVo extends News implements Serializable {
    private String createName;
}
