package com.nanyang.academy.entity.dto;

import com.nanyang.academy.entity.Promotion;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="{PromotionVo}", description="PromotionVo信息")
public class PromotionVo extends Promotion implements Serializable {
    private String createName;
}
