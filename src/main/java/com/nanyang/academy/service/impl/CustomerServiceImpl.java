package com.nanyang.academy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanyang.academy.entity.Customer;
import com.nanyang.academy.entity.dto.CustomerVo;
import com.nanyang.academy.entity.dto.NewsVo;
import com.nanyang.academy.entity.param.CustomerQueryParam;
import com.nanyang.academy.mapper.CustomerMapper;
import com.nanyang.academy.service.CustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public IPage<Customer> getCustomerListPage(CustomerQueryParam param) {
        Page<Customer> page = new Page<>(param.getCurrent(),param.getSize());
        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(param.getName()))
            wrapper.like("name",param.getName());

        if (ObjectUtils.isNotEmpty(param.getProject()))
            wrapper.like("project",param.getProject());
        if (ObjectUtils.isNotEmpty(param.getLanguage()))
            wrapper.eq("language",param.getLanguage());
        IPage<Customer> res = customerMapper.selectPage(page,wrapper);
        return res;
    }

    @Override
    public List<Map<String, Object>> getPieData() {//CustomerQueryParam param
        /*QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(param.getName()))
            wrapper.eq("name",param.getName());
        if (ObjectUtils.isNotEmpty(param.getLanguage()))
            wrapper.eq("language",param.getLanguage());*/
        List<Map<String, Object>> res = customerMapper.getPieData();
        return res;
    }
}
