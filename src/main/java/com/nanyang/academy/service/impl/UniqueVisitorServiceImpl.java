package com.nanyang.academy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.nanyang.academy.entity.UniqueVisitor;
import com.nanyang.academy.mapper.CustomerMapper;
import com.nanyang.academy.mapper.PageViewMapper;
import com.nanyang.academy.mapper.UniqueVisitorMapper;
import com.nanyang.academy.service.UniqueVisitorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nanyang.academy.utils.http.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UniqueVisitorServiceImpl extends ServiceImpl<UniqueVisitorMapper, UniqueVisitor> implements UniqueVisitorService {

    @Autowired
    private UniqueVisitorMapper uniqueVisitorMapper;

    @Autowired
    private PageViewMapper viewMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public void increase(HttpServletRequest request) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        viewMapper.increase();
        String ip = IpUtils.getIpAddr(request);
        String date = df.format(new Date());
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("ip",ip);
        wrapper.eq("date",date);
        UniqueVisitor visitor = uniqueVisitorMapper.selectOne(wrapper);
        if (ObjectUtils.isEmpty(visitor)){
            visitor = new UniqueVisitor();
            visitor.setDate(date);
            visitor.setIp(ip);
            uniqueVisitorMapper.insert(visitor);
        }
    }

    @Override
    public Map<String, Object> getViewCount(HttpServletRequest request) {
        Integer uniqueVisitor = uniqueVisitorMapper.getCount();
        Integer pageView = viewMapper.getPageView();
        QueryWrapper wrapper = new QueryWrapper();
        Long customerNum = customerMapper.selectCount(wrapper);

        Map<String, Object> res = new HashMap<>();
        res.put("uniqueVisitor",uniqueVisitor);
        res.put("pageView",pageView);
        res.put("customerNum",customerNum);
        return res;
    }
}
