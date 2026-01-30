package com.nanyang.academy.service;

import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.entity.BusinessParam;
import com.nanyang.academy.entity.SysInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nanyang.academy.entity.pojo.CompanyParam;

public interface SysInfoService extends IService<SysInfo> {

    ResultEntity<BusinessParam> getBusiness();

    ResultEntity<CompanyParam> getCompany();
}
