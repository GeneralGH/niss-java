package com.nanyang.academy.entity.dto;

import com.nanyang.academy.entity.Outstanding;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="{OutstandingVo}", description="OutstandingVo信息")
public class OutstandingVo extends Outstanding implements Serializable {
    private String createName;
}
