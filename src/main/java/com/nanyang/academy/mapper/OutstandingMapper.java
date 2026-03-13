package com.nanyang.academy.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanyang.academy.entity.Outstanding;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nanyang.academy.entity.dto.OutstandingVo;
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
public interface OutstandingMapper extends BaseMapper<Outstanding> {

    IPage<OutstandingVo> getOutstandingListPage(Page<OutstandingVo> page, @Param(Constants.WRAPPER) QueryWrapper<OutstandingVo> wrapper);

    @Select("select sort from outstanding order by sort desc limit 1")
    Integer getLastSort();

    Outstanding getLast(Long id);

    Outstanding getNext(Long id);
}
