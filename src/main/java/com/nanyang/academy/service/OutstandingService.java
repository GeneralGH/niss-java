package com.nanyang.academy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nanyang.academy.entity.Outstanding;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nanyang.academy.entity.dto.OutstandingLastAndNextVo;
import com.nanyang.academy.entity.dto.OutstandingVo;
import com.nanyang.academy.entity.param.OutstandingQueryParam;
import com.nanyang.academy.entity.param.SortChangeParam;

public interface OutstandingService extends IService<Outstanding> {
    IPage<OutstandingVo> getOutstandingListPage(OutstandingQueryParam param);

    void editSort(SortChangeParam param);

    OutstandingLastAndNextVo LastAndNextVo(Long id);

    Integer getLastSort();
}
