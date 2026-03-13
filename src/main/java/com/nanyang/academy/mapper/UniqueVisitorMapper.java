package com.nanyang.academy.mapper;

import com.nanyang.academy.entity.UniqueVisitor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface UniqueVisitorMapper extends BaseMapper<UniqueVisitor> {

    @Select("select count(1) from unique_visitor")
    Integer getCount();

}
