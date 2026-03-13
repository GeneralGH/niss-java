package com.nanyang.academy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nanyang.academy.entity.CustomerSource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nanyang.academy.entity.param.SourceQueryParam;

import java.util.List;
import java.util.Map;

public interface CustomerSourceService extends IService<CustomerSource> {

    IPage<CustomerSource> getSourceListPage(SourceQueryParam param);

   /* List<Map<String, Object>> getVisitNumStatistics();*/
}
