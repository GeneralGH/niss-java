package com.nanyang.academy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nanyang.academy.entity.ActivityNotice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nanyang.academy.entity.dto.ProjectAdmissionVo;
import com.nanyang.academy.entity.param.ProjectAdmissionQueryParam;

/**
 * <p>
 * 活动预告表 服务类
 * </p>
 *
 * @author yiyu
 * @since 2026-03-17
 */
public interface IActivityNoticeService extends IService<ActivityNotice> {

    IPage<ProjectAdmissionVo> getProjectAdmissionListPage(ProjectAdmissionQueryParam param);
}
