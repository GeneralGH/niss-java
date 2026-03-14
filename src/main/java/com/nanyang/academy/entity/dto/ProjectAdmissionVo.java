package com.nanyang.academy.entity.dto;
import com.nanyang.academy.entity.ProjectAdmission;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "{ProjectAdmissionVo}", description = "项目与招生VO")
public class ProjectAdmissionVo extends ProjectAdmission implements Serializable {
    private Long id;
    private String headImage;
    private String headImageEn;
    private String intro;
    private String introEn;
    private String activityTime;
    private String activityTimeEn;
    private String icon;
    private String iconEn;
    private Integer sort;
}