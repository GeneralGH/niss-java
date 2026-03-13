package com.nanyang.academy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanyang.academy.entity.Teacher;
import com.nanyang.academy.entity.dto.TeacherVo;
import com.nanyang.academy.entity.dto.TeacherVo;
import com.nanyang.academy.entity.param.SortChangeParam;
import com.nanyang.academy.entity.param.TeacherQueryParam;
import com.nanyang.academy.entity.param.TeacherQueryParam;
import com.nanyang.academy.mapper.TeacherMapper;
import com.nanyang.academy.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public IPage<TeacherVo> getTeacherListPage(TeacherQueryParam param) {
        Page<TeacherVo> page = new Page<>(param.getCurrent(),param.getSize());
        QueryWrapper<TeacherVo> wrapper = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(param.getName())){
            wrapper.and(w->{w.like("t.name",param.getName()).or().like("t.name_en",param.getName());});
        }
        if(ObjectUtils.isNotEmpty(param.getFirst()))
            wrapper.likeRight("pinyin",param.getFirst());
        if(ObjectUtils.isNotEmpty(param.getElite()))
            wrapper.like("elite",param.getElite());
        if (ObjectUtils.isNotEmpty(param.getLanguage())){
            wrapper.eq("t.language",param.getLanguage());
        }
        //wrapper.orderByAsc("name");
        wrapper.orderByAsc("t.sort");
        IPage<TeacherVo> res = teacherMapper.getTeacherListPage(page,wrapper);
        return res;
    }

    @Override
    public void editSort(SortChangeParam param) {
        Teacher t1 = teacherMapper.selectById(param.getFirst());
        Teacher t2 = teacherMapper.selectById(param.getSecond());
        Integer first = t1.getSort();
        Integer second = t2.getSort();
        t1.setSort(second);
        t2.setSort(first);
        teacherMapper.updateById(t1);
        teacherMapper.updateById(t2);
    }

    @Override
    public Integer getLastSort() {
        Integer sort = teacherMapper.getLastSort();
        return sort != null?sort:0;
    }
}
