package com.nanyang.academy.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanyang.academy.entity.News;
import com.nanyang.academy.entity.dto.NewsVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author HengYuanKeJi
 */
@Component
public interface NewsMapper extends BaseMapper<News> {

    IPage<NewsVo> getNewsListPage(Page<NewsVo> page,@Param(Constants.WRAPPER)  QueryWrapper<NewsVo> wrapper);

    News getLast(News news);

    News getNext(News news);

    @Select("select sort from news where type = #{type} order by sort desc limit 1")
    Integer getLastSort(@Param("type") Integer type);
}
