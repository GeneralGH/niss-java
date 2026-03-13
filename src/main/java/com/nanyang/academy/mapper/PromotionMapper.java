package com.nanyang.academy.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanyang.academy.entity.Outstanding;
import com.nanyang.academy.entity.Promotion;
import com.nanyang.academy.entity.dto.PromotionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PromotionMapper extends BaseMapper<Promotion> {

    IPage<PromotionVo> getPromotionListPage(Page<PromotionVo> page,@Param(Constants.WRAPPER) QueryWrapper<PromotionVo> wrapper);

    @Select("select sort from promotion order by sort desc limit 1")
    Integer getLastSort();

    Promotion getLast(Long id);

    Promotion getNext(Long id);
}
