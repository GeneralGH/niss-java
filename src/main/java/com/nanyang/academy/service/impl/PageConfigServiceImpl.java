package com.nanyang.academy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanyang.academy.entity.PageConfig;
import com.nanyang.academy.entity.dto.NewsVo;
import com.nanyang.academy.entity.param.PageConfigQueryParam;
import com.nanyang.academy.mapper.PageConfigMapper;
import com.nanyang.academy.service.PageConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageConfigServiceImpl extends ServiceImpl<PageConfigMapper, PageConfig> implements PageConfigService {

    @Autowired
    private PageConfigMapper pageConfigMapper;

    @Override
    public IPage<PageConfig> getPageConfigListPage(PageConfigQueryParam param) {
        Page<PageConfig> page = new Page<>(param.getCurrent(),param.getSize());
        QueryWrapper<PageConfig> wrapper = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(param.getPosition())){
            wrapper.eq("position",param.getPosition());
        }
        //wrapper.eq("position",param.getPosition());
        if (ObjectUtils.isNotEmpty(param.getLanguage())){
            wrapper.eq("language",param.getLanguage());
        }
        IPage<PageConfig> res = pageConfigMapper.selectPage(page,wrapper);
        return res;
    }
}
