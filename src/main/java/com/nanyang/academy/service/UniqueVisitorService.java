package com.nanyang.academy.service;

import com.nanyang.academy.entity.UniqueVisitor;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface UniqueVisitorService extends IService<UniqueVisitor> {
    void increase(HttpServletRequest request);

    Map<String, Object> getViewCount(HttpServletRequest request);
}
