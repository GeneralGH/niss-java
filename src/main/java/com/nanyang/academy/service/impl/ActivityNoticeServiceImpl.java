package com.nanyang.academy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanyang.academy.entity.ActivityNotice;
import com.nanyang.academy.entity.dto.ActivityNoticeVo;
import com.nanyang.academy.entity.param.ActivityNoticeQueryParam;
import com.nanyang.academy.mapper.ActivityNoticeMapper;
import com.nanyang.academy.mapper.AlumniMapper;
import com.nanyang.academy.service.ActivityNoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 活动预告表 服务实现类
 * </p>
 *
 * @author yiyu
 * @since 2026-03-17
 */
@Service
public class ActivityNoticeServiceImpl extends ServiceImpl<ActivityNoticeMapper, ActivityNotice> implements ActivityNoticeService {
    @Autowired
    private ActivityNoticeMapper activityNoticeMapper;
    QueryWrapper<ActivityNoticeVo> wrapper = new QueryWrapper<>();

    public List<ActivityNoticeVo> getActivityNoticeListPage(ActivityNoticeQueryParam param){
        Page<ActivityNoticeVo> page = new Page<>(param.getCurrent(), param.getSize());
        return activityNoticeMapper.getActivityNoticePage(page, wrapper);
    }


}