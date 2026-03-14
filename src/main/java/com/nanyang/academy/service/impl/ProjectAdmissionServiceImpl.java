package com.nanyang.academy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nanyang.academy.entity.ProjectAdmission;
import com.nanyang.academy.entity.param.ProjectAdmissionQueryParam;
import com.nanyang.academy.entity.dto.ProjectAdmissionVo;
import com.nanyang.academy.mapper.ProjectAdmissionMapper;
import com.nanyang.academy.service.ProjectAdmissionService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

@Service
public class ProjectAdmissionServiceImpl extends ServiceImpl<ProjectAdmissionMapper, ProjectAdmission> implements ProjectAdmissionService {

    @Override
    public IPage<ProjectAdmissionVo> getProjectAdmissionListPage(ProjectAdmissionQueryParam param) {
        Page<ProjectAdmissionVo> page = new Page<>(param.getCurrent(), param.getSize());
        QueryWrapper<ProjectAdmissionVo> wrapper = new QueryWrapper<>();

        if (ObjectUtils.isNotEmpty(param.getIntro())) {
            wrapper.and(w -> w.like("t.intro", param.getIntro()).or().like("t.intro_en", param.getIntro()));
        }

        wrapper.orderByAsc("t.sort");
        return baseMapper.getProjectAdmissionListPage(page, wrapper);
    }
}