package com.nanyang.academy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nanyang.academy.entity.News;
import com.nanyang.academy.entity.dto.LastAndNextVo;
import com.nanyang.academy.entity.dto.NewsVo;
import com.nanyang.academy.entity.param.NewsQueryParam;
import com.nanyang.academy.entity.param.SortChangeParam;

public interface NewsService extends IService<News> {
    IPage<NewsVo> getNewsListPage(NewsQueryParam param);

    LastAndNextVo LastAndNextVo(News news);

    void editSort(SortChangeParam param);

    Integer getLastSortBytype(Integer type);


}
