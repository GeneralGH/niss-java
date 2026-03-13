package com.nanyang.academy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanyang.academy.entity.Course;
import com.nanyang.academy.entity.Course;
import com.nanyang.academy.entity.dto.CourseVo;
import com.nanyang.academy.entity.dto.CourseVo;
import com.nanyang.academy.entity.param.CourseQueryParam;
import com.nanyang.academy.entity.param.CourseQueryParam;
import com.nanyang.academy.entity.param.SortChangeParam;
import com.nanyang.academy.mapper.CourseMapper;
import com.nanyang.academy.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public IPage<CourseVo> getCourseListPage(CourseQueryParam param) {
        Page<CourseVo> page = new Page<>(param.getCurrent(),param.getSize());
        QueryWrapper<CourseVo> wrapper = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(param.getType())){
            wrapper.eq("t.type",param.getType());
        }
        if (ObjectUtils.isNotEmpty(param.getTitle())){
            wrapper.and(w->{w.like("t.title",param.getTitle()).or().like("t.title_en",param.getTitle());});
        }
        wrapper.orderByAsc("t.id");
        IPage<CourseVo> res = courseMapper.getCourseListPage(page,wrapper);
        return res;
    }


    @Override
    public Integer getLastSort() {
        Integer sort = courseMapper.getLastSort();
        return sort != null?sort:0;
    }

    @Override
    public void editSort(SortChangeParam param) {
        Course t1 = courseMapper.selectById(param.getFirst());
        Course t2 = courseMapper.selectById(param.getSecond());
        Integer first = t1.getSort();
        Integer second = t2.getSort();
        t1.setSort(second);
        t2.setSort(first);
        courseMapper.updateById(t1);
        courseMapper.updateById(t2);
    }
}
