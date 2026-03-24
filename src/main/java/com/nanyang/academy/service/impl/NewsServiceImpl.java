package com.nanyang.academy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nanyang.academy.entity.Banner;
import com.nanyang.academy.entity.News;
import com.nanyang.academy.entity.dto.LastAndNextVo;
import com.nanyang.academy.entity.dto.NewsVo;
import com.nanyang.academy.entity.param.NewsQueryParam;
import com.nanyang.academy.entity.param.SortChangeParam;
import com.nanyang.academy.mapper.NewsMapper;
import com.nanyang.academy.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public IPage<NewsVo> getNewsListPage(NewsQueryParam param) {
        Page<NewsVo> page = new Page<>(param.getCurrent(),param.getSize());
        QueryWrapper<NewsVo> wrapper = new QueryWrapper<>();
        wrapper.eq("t.type",param.getType());
        if (param.getHighlight() != null) {
            if (param.getHighlight() == 1) {
                // 查推荐：必须 =1
                wrapper.eq("t.highlight", 1);
            } else {
                // 查非推荐：=0 或者 IS NULL
                wrapper.and(w -> w
                        .eq("t.highlight", 0)
                        .or()
                        .isNull("t.highlight")
                );
            }
        }
        if (ObjectUtils.isNotEmpty(param.getLanguage())){
            wrapper.eq("t.language",param.getLanguage());
        }
        if (ObjectUtils.isNotEmpty(param.getTitle())){
            wrapper.and(w->{w.like("t.title",param.getTitle()).or().like("t.title_en",param.getTitle());});
        }
        if (ObjectUtils.isNotEmpty(param.getAuthor())){
            //wrapper.eq("t.author",param.getAuthor());
            wrapper.and(w->{w.like("t.author",param.getAuthor()).or().like("t.author_en",param.getAuthor());});
        }
        wrapper.orderByDesc("t.sort");
        IPage<NewsVo> res = newsMapper.getNewsListPage(page,wrapper);
        return res;
    }

    @Override
    public LastAndNextVo LastAndNextVo(News news) {
        LastAndNextVo nextVo = new LastAndNextVo();
        News last = newsMapper.getLast(news);
        nextVo.setLast(last);
        News next = newsMapper.getNext(news);
        nextVo.setNext(next);
        return nextVo;
    }

    @Override
    public void editSort(SortChangeParam param) {
        News t1 = newsMapper.selectById(param.getFirst());
        News t2 = newsMapper.selectById(param.getSecond());
        Integer first = t1.getSort();
        Integer second = t2.getSort();
        t1.setSort(second);
        t2.setSort(first);
        newsMapper.updateById(t1);
        newsMapper.updateById(t2);
    }

    @Override
    public Integer getLastSortBytype(Integer type) {
        Integer sort = newsMapper.getLastSort(type);
        return sort != null?sort:0;
    }
}
