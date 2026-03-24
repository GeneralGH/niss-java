package com.nanyang.academy.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanyang.academy.entity.ActivityNotice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nanyang.academy.entity.dto.ActivityNoticeVo;
import com.nanyang.academy.entity.dto.ProjectAdmissionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
/**
 * <p>
 * 活动预告表 Mapper 接口
 * </p>
 *
 * @author yiyu
 * @since 2026-03-17
 */
@Mapper
public interface ActivityNoticeMapper extends BaseMapper<ActivityNotice> {
    IPage<ActivityNotice> getActivityNoticePage(Page<ActivityNotice> page, @Param(Constants.WRAPPER) QueryWrapper<ActivityNotice> wrapper);
}
