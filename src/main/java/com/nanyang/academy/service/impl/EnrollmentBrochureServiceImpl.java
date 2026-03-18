package com.nanyang.academy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanyang.academy.entity.EnrollmentBrochure;
import com.nanyang.academy.entity.dto.ActivityNoticeVo;
import com.nanyang.academy.entity.param.ActivityNoticeQueryParam;
import com.nanyang.academy.entity.param.EnrollmentBrochureQueryParam;
import com.nanyang.academy.mapper.ActivityNoticeMapper;
import com.nanyang.academy.mapper.EnrollmentBrochureMapper;
import com.nanyang.academy.service.IEnrollmentBrochureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 招生简章文件表 服务实现类
 * </p>
 *
 * @author yiyu
 * @since 2026-03-17
 */
@Service
public class EnrollmentBrochureServiceImpl extends ServiceImpl<EnrollmentBrochureMapper, EnrollmentBrochure> implements IEnrollmentBrochureService {
    @Autowired
    private EnrollmentBrochureMapper enrollmentBrochureMapper;
    QueryWrapper<EnrollmentBrochure> wrapper = new QueryWrapper<>();

    public List<EnrollmentBrochure> getEnrollmentBrochureListPage(EnrollmentBrochureQueryParam param){
        Page<EnrollmentBrochure> page = new Page<>(param.getCurrent(), param.getSize());
        return enrollmentBrochureMapper.getEnrollmentBrochurePage(page, wrapper);
    }
}
