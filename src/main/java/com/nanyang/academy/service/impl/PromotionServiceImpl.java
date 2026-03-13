package com.nanyang.academy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nanyang.academy.entity.Promotion;
import com.nanyang.academy.entity.Promotion;
import com.nanyang.academy.entity.dto.PromotionLastAndNextVo;
import com.nanyang.academy.entity.dto.PromotionVo;
import com.nanyang.academy.entity.param.PromotionQueryParam;
import com.nanyang.academy.entity.param.SortChangeParam;
import com.nanyang.academy.mapper.PromotionMapper;
import com.nanyang.academy.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionServiceImpl extends ServiceImpl<PromotionMapper, Promotion> implements PromotionService {

    @Autowired
    private PromotionMapper PromotionMapper;

    @Override
    public IPage<PromotionVo> getPromotionListPage(PromotionQueryParam param) {
        Page<PromotionVo> page = new Page<>(param.getCurrent(),param.getSize());
        QueryWrapper<PromotionVo> wrapper = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(param.getLanguage())){
            wrapper.eq("t.language",param.getLanguage());
        }
        if (ObjectUtils.isNotEmpty(param.getTitle())){
            wrapper.and(w->{w.like("t.title",param.getTitle()).or().like("t.title_en",param.getTitle());});
        }
        wrapper.orderByAsc("t.sort");
        IPage<PromotionVo> res = PromotionMapper.getPromotionListPage(page,wrapper);
        return res;
    }

    @Override
    public void editSort(SortChangeParam param) {
        Promotion t1 = PromotionMapper.selectById(param.getFirst());
        Promotion t2 = PromotionMapper.selectById(param.getSecond());
        Integer first = t1.getSort();
        Integer second = t2.getSort();
        t1.setSort(second);
        t2.setSort(first);
        PromotionMapper.updateById(t1);
        PromotionMapper.updateById(t2);
    }


    @Override
    public Integer getLastSort() {
        Integer sort = PromotionMapper.getLastSort();
        return sort != null?sort:0;
    }

    @Override
    public PromotionLastAndNextVo LastAndNextVo(Long id) {
        PromotionLastAndNextVo res = new PromotionLastAndNextVo();
        Promotion last = PromotionMapper.getLast(id);
        res.setLast(last);
        Promotion next = PromotionMapper.getNext(id);
        res.setNext(next);
        return res;
    }
}
