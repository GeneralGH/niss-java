package com.nanyang.academy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanyang.academy.entity.Customer;
import com.nanyang.academy.entity.CustomerSource;
import com.nanyang.academy.entity.param.SourceQueryParam;
import com.nanyang.academy.mapper.CustomerSourceMapper;
import com.nanyang.academy.service.CustomerSourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerSourceServiceImpl extends ServiceImpl<CustomerSourceMapper, CustomerSource> implements CustomerSourceService {

    @Autowired
    private CustomerSourceMapper customerSourceMapper;

    @Override
    public IPage<CustomerSource> getSourceListPage(SourceQueryParam param) {
        Page<CustomerSource> page = new Page<>(param.getCurrent(),param.getSize());
        QueryWrapper<CustomerSource> wrapper = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(param.getName()))
            wrapper.like("t.name",param.getName());
        wrapper.orderByDesc("t.create_time");
        IPage<CustomerSource> res = customerSourceMapper.getSourceListPage(page,wrapper);
        return res;
    }

    /*@Override
    public List<CustomerSource> getVisitNumStatistics() {
        List<CustomerSource> res = customerSourceMapper.sele();
        return res;
    }*/
}
