package com.nanyang.academy.entity.dto;

import com.nanyang.academy.entity.Banner;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="{BannerVo}", description="BannerVo信息")
public class BannerVo extends Banner implements Serializable {
    private String createName;
}
