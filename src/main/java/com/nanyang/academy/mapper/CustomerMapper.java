package com.nanyang.academy.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nanyang.academy.entity.Customer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author HengYuanKeJi
 */
@Component
public interface CustomerMapper extends BaseMapper<Customer> {

    @Select("select s.name,count(1) `value` from customer t " +
            "             left join customer_source s on t.source = s.source_key " +
            "             group by t.source,s.`name`")
    List<Map<String, Object>> getPieData();

}
