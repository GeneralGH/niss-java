package com.nanyang.academy.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PageViewMapper {


    @Update("update page_view set view_count = view_count + 1")
    int increase();


    @Select("select view_count from page_view")
    Integer getPageView();
}
