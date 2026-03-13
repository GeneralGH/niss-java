package com.nanyang.academy.entity.dto;

import com.nanyang.academy.entity.CustomerSource;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="{CustomerSourceVo}", description="CustomerSourceVo")
public class CustomerSourceVo extends CustomerSource implements Serializable {
    private String createName;
}
