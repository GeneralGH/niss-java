package com.nanyang.academy.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanyang.academy.entity.Alumni;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nanyang.academy.entity.dto.AlumniVo;
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
public interface AlumniMapper extends BaseMapper<Alumni> {
    IPage<AlumniVo> getAlumniListPage(Page<AlumniVo> page, @Param(Constants.WRAPPER) QueryWrapper<AlumniVo> wrapper);

    @Select("select sort from alumni order by sort desc limit 1")
    Integer getLastSort();
}
