package com.nanyang.academy.controller;

import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.entity.Banner;
import com.nanyang.academy.mapper.PageViewMapper;
import com.nanyang.academy.service.UniqueVisitorService;
import com.nanyang.academy.utils.http.IpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/view")
@Api(tags = "访问量")
public class ViewController {


    @Autowired
    private UniqueVisitorService uniqueVisitorService;


    @PostMapping("/addPageView")
    @ApiOperation(value = "新增访问")
    public void addPageView(HttpServletRequest request) {
        uniqueVisitorService.increase(request);
    }


    @PostMapping("/getViewCount")
    @ApiOperation(value = "获取访问量")
    public ResultEntity getViewCount(HttpServletRequest request) {
        Map<String,Object> res = uniqueVisitorService.getViewCount(request);
        return ResultEntity.getOkResult(res);
    }
}
