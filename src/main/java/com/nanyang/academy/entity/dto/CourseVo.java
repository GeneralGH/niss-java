package com.nanyang.academy.entity.dto;

import com.nanyang.academy.entity.Alumni;
import com.nanyang.academy.entity.Course;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="{CourseVo}", description="CourseVo信息")
public class CourseVo  extends Course implements Serializable {
    private String createName;
}
