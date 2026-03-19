package com.nanyang.academy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nanyang.academy.entity.ActivityNotice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nanyang.academy.entity.dto.ActivityNoticeVo;
import com.nanyang.academy.entity.param.ActivityNoticeQueryParam;

import java.util.List;

public interface ActivityNoticeService extends IService<ActivityNotice> {

    IPage<ActivityNoticeVo> getActivityNoticeListPage(ActivityNoticeQueryParam param);

}
