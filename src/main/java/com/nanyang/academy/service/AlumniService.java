package com.nanyang.academy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nanyang.academy.entity.Alumni;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nanyang.academy.entity.dto.AlumniVo;
import com.nanyang.academy.entity.param.AlumniQueryParam;
import com.nanyang.academy.entity.param.SortChangeParam;

public interface AlumniService extends IService<Alumni> {
    IPage<AlumniVo> getAlumniListPage(AlumniQueryParam param);

    void editSort(SortChangeParam param);
    Integer getLastSort();
}
