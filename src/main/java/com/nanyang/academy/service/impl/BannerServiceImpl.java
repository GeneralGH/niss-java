package com.nanyang.academy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nanyang.academy.entity.Banner;
import com.nanyang.academy.entity.Banner;
import com.nanyang.academy.entity.dto.BannerVo;
import com.nanyang.academy.entity.param.BannerQueryParam;
import com.nanyang.academy.entity.param.SortChangeParam;
import com.nanyang.academy.mapper.BannerMapper;
import com.nanyang.academy.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public IPage<BannerVo> getBannerListPage(BannerQueryParam param) {
        Page<BannerVo> page = new Page<>(param.getCurrent(),param.getSize());
        QueryWrapper<BannerVo> wrapper = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(param.getLanguage())){
            wrapper.eq("t.language",param.getLanguage());
        }
        if (ObjectUtils.isNotEmpty(param.getTitle())){
            wrapper.and(w->{w.like("t.title",param.getTitle()).or().like("t.title_en",param.getTitle());});
        }
        if (ObjectUtils.isNotEmpty(param.getIsShow())){
            wrapper.eq("t.is_show",param.getIsShow());
        }
        wrapper.orderByAsc("t.sort");
        IPage<BannerVo> res = bannerMapper.getBannerListPage(page,wrapper);
        return res;
    }

    @Override
    public void editSort(SortChangeParam param) {
        Banner t1 = bannerMapper.selectById(param.getFirst());
        Banner t2 = bannerMapper.selectById(param.getSecond());
        Integer first = t1.getSort();
        Integer second = t2.getSort();
        t1.setSort(second);
        t2.setSort(first);
        bannerMapper.updateById(t1);
        bannerMapper.updateById(t2);
    }

    @Override
    public Integer getLastSort() {
        Integer sort = bannerMapper.getLastSort();
        return sort != null?sort:0;
    }
}
