package com.nanyang.academy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nanyang.academy.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nanyang.academy.entity.dto.CourseVo;
import com.nanyang.academy.entity.param.CourseQueryParam;
import com.nanyang.academy.entity.param.SortChangeParam;

public interface CourseService extends IService<Course> {


    Integer getLastSort();

    void editSort(SortChangeParam param);

    IPage<CourseVo> getCourseListPage(CourseQueryParam param);
}
