package com.nanyang.academy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nanyang.academy.entity.Customer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nanyang.academy.entity.dto.CustomerVo;
import com.nanyang.academy.entity.param.CustomerQueryParam;

import java.util.List;
import java.util.Map;

public interface CustomerService extends IService<Customer> {

    IPage<Customer>  getCustomerListPage(CustomerQueryParam param);


    List<Map<String, Object>> getPieData();//CustomerQueryParam param
}
