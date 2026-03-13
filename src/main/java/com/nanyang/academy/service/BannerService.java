package com.nanyang.academy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nanyang.academy.entity.Banner;
import com.nanyang.academy.entity.dto.BannerVo;
import com.nanyang.academy.entity.param.BannerQueryParam;
import com.nanyang.academy.entity.param.SortChangeParam;

public interface BannerService extends IService<Banner> {

    IPage<BannerVo> getBannerListPage(BannerQueryParam param);

    void editSort(SortChangeParam param);

    Integer getLastSort();
}
