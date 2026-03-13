package com.nanyang.academy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nanyang.academy.entity.PageConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nanyang.academy.entity.param.PageConfigQueryParam;

public interface PageConfigService extends IService<PageConfig> {
    IPage<PageConfig> getPageConfigListPage(PageConfigQueryParam param);
}
