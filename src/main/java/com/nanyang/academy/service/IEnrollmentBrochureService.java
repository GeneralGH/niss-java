package com.nanyang.academy.service;

import com.nanyang.academy.entity.EnrollmentBrochure;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nanyang.academy.entity.param.EnrollmentBrochureQueryParam;

import java.util.List;

/**
 * <p>
 * 招生简章文件表 服务类
 * </p>
 *
 * @author yiyu
 * @since 2026-03-17
 */
public interface IEnrollmentBrochureService extends IService<EnrollmentBrochure> {
    List<EnrollmentBrochure> getEnrollmentBrochureListPage(EnrollmentBrochureQueryParam param);
}
