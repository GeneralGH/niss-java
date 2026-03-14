package com.nanyang.academy.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nanyang.academy.entity.ProjectAdmission;
import com.nanyang.academy.entity.dto.ProjectAdmissionVo;
import org.apache.ibatis.annotations.Param;

public interface ProjectAdmissionMapper extends BaseMapper<ProjectAdmission> {
    IPage<ProjectAdmissionVo> getProjectAdmissionListPage(IPage<ProjectAdmissionVo> page, @Param("ew") QueryWrapper<ProjectAdmissionVo> wrapper);
}
