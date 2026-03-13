package com.nanyang.academy.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanyang.academy.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nanyang.academy.entity.dto.CourseVo;
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
public interface CourseMapper extends BaseMapper<Course> {

    IPage<CourseVo> getCourseListPage(Page<CourseVo> page, QueryWrapper<CourseVo> wrapper);

    @Select("select sort from course order by sort desc limit 1")
    Integer getLastSort();

}
