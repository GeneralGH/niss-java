package com.nanyang.academy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanyang.academy.entity.Alumni;
import com.nanyang.academy.entity.Alumni;
import com.nanyang.academy.entity.dto.AlumniVo;
import com.nanyang.academy.entity.dto.AlumniVo;
import com.nanyang.academy.entity.param.AlumniQueryParam;
import com.nanyang.academy.entity.param.SortChangeParam;
import com.nanyang.academy.mapper.AlumniMapper;
import com.nanyang.academy.service.AlumniService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlumniServiceImpl extends ServiceImpl<AlumniMapper, Alumni> implements AlumniService {

    @Autowired
    private AlumniMapper alumniMapper;

    @Override
    public IPage<AlumniVo> getAlumniListPage(AlumniQueryParam param) {
        Page<AlumniVo> page = new Page<>(param.getCurrent(),param.getSize());
        QueryWrapper<AlumniVo> wrapper = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(param.getLanguage())){
            wrapper.eq("t.language",param.getLanguage());
        }
        if (ObjectUtils.isNotEmpty(param.getName())){
            wrapper.and(w->{w.like("t.name",param.getName()).or().like("t.name_en",param.getName());});
        }
        wrapper.orderByAsc("t.sort");
        IPage<AlumniVo> res = alumniMapper.getAlumniListPage(page,wrapper);
        return res;
    }

    @Override
    public void editSort(SortChangeParam param) {
        Alumni t1 = alumniMapper.selectById(param.getFirst());
        Alumni t2 = alumniMapper.selectById(param.getSecond());
        Integer first = t1.getSort();
        Integer second = t2.getSort();
        t1.setSort(second);
        t2.setSort(first);
        alumniMapper.updateById(t1);
        alumniMapper.updateById(t2);
    }

    @Override
    public Integer getLastSort() {
        Integer sort = alumniMapper.getLastSort();
        return sort != null?sort:0;
    }
}
