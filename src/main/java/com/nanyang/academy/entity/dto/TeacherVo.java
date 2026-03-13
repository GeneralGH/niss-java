package com.nanyang.academy.entity.dto;

import com.nanyang.academy.entity.Teacher;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="{TeacherVo}", description="TeacherVo信息")
public class TeacherVo extends Teacher implements Serializable {
    private String createName;
}
