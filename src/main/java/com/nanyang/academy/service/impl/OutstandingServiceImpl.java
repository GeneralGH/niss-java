package com.nanyang.academy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanyang.academy.entity.Outstanding;
import com.nanyang.academy.entity.Outstanding;
import com.nanyang.academy.entity.dto.AlumniVo;
import com.nanyang.academy.entity.dto.OutstandingLastAndNextVo;
import com.nanyang.academy.entity.dto.OutstandingVo;
import com.nanyang.academy.entity.param.OutstandingQueryParam;
import com.nanyang.academy.entity.param.SortChangeParam;
import com.nanyang.academy.mapper.OutstandingMapper;
import com.nanyang.academy.service.OutstandingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutstandingServiceImpl extends ServiceImpl<OutstandingMapper, Outstanding> implements OutstandingService {

    @Autowired
    private OutstandingMapper outstandingMapper;

    @Override
    public IPage<OutstandingVo> getOutstandingListPage(OutstandingQueryParam param) {
        Page<OutstandingVo> page = new Page<>(param.getCurrent(),param.getSize());
        QueryWrapper<OutstandingVo> wrapper = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(param.getLanguage())){
            wrapper.eq("t.language",param.getLanguage());
        }
        if (ObjectUtils.isNotEmpty(param.getName())){
            wrapper.and(w->{w.like("t.name",param.getName()).or().like("t.name_en",param.getName());});
        }
        wrapper.orderByAsc("t.sort");
        IPage<OutstandingVo> res = outstandingMapper.getOutstandingListPage(page,wrapper);
        return res;
    }

    @Override
    public void editSort(SortChangeParam param) {
        Outstanding t1 = outstandingMapper.selectById(param.getFirst());
        Outstanding t2 = outstandingMapper.selectById(param.getSecond());
        Integer first = t1.getSort();
        Integer second = t2.getSort();
        t1.setSort(second);
        t2.setSort(first);
        outstandingMapper.updateById(t1);
        outstandingMapper.updateById(t2);
    }

    @Override
    public OutstandingLastAndNextVo LastAndNextVo(Long id) {
        OutstandingLastAndNextVo res = new OutstandingLastAndNextVo();
        Outstanding last = outstandingMapper.getLast(id);
        res.setLast(last);
        Outstanding next = outstandingMapper.getNext(id);
        res.setNext(next);
        return res;
    }

    @Override
    public Integer getLastSort() {
        Integer sort = outstandingMapper.getLastSort();
        return sort != null?sort:0;
    }
}
