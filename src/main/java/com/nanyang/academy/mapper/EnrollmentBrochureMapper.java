package com.nanyang.academy.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanyang.academy.entity.EnrollmentBrochure;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nanyang.academy.entity.dto.ActivityNoticeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 招生简章文件表 Mapper 接口
 * </p>
 *
 * @author yiyu
 * @since 2026-03-17
 */
@Mapper
public interface EnrollmentBrochureMapper extends BaseMapper<EnrollmentBrochure> {
    List<EnrollmentBrochure> getEnrollmentBrochurePage(Page<EnrollmentBrochure> page, @Param(Constants.WRAPPER) QueryWrapper<EnrollmentBrochure> wrapper);
}
