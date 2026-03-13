package com.nanyang.academy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nanyang.academy.entity.Promotion;
import com.nanyang.academy.entity.dto.PromotionLastAndNextVo;
import com.nanyang.academy.entity.dto.PromotionVo;
import com.nanyang.academy.entity.param.PromotionQueryParam;
import com.nanyang.academy.entity.param.SortChangeParam;

public interface PromotionService extends IService<Promotion> {
    IPage<PromotionVo> getPromotionListPage(PromotionQueryParam param);

    void editSort(SortChangeParam param);

    PromotionLastAndNextVo LastAndNextVo(Long id);

    Integer getLastSort();
}
