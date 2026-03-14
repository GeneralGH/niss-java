package com.nanyang.academy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nanyang.academy.entity.ProjectAdmission;
import com.nanyang.academy.entity.param.ProjectAdmissionQueryParam;
import com.nanyang.academy.entity.dto.ProjectAdmissionVo;

public interface ProjectAdmissionService extends IService<ProjectAdmission> {
    IPage<ProjectAdmissionVo> getProjectAdmissionListPage(ProjectAdmissionQueryParam param);
}