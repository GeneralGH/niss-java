package com.nanyang.academy.entity.dto;

import com.nanyang.academy.entity.Alumni;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="{AlumniVo}", description="AlumniVo信息")
public class AlumniVo extends Alumni implements Serializable {
    private String createName;
}
