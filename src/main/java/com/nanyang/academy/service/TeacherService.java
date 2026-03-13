package com.nanyang.academy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nanyang.academy.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nanyang.academy.entity.dto.TeacherVo;
import com.nanyang.academy.entity.param.SortChangeParam;
import com.nanyang.academy.entity.param.TeacherQueryParam;

public interface TeacherService extends IService<Teacher> {
    IPage<TeacherVo> getTeacherListPage(TeacherQueryParam param);

    void editSort(SortChangeParam param);

    Integer getLastSort();
}
