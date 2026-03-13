package com.nanyang.academy.entity.dto;

import com.nanyang.academy.entity.Outstanding;
import com.nanyang.academy.entity.Promotion;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="{PromotionLastAndNextVo}", description="PromotionLastAndNextVo")
public class PromotionLastAndNextVo implements Serializable {

    private Promotion last;

    private Promotion next;
}
