package com.nanyang.academy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.entity.param.PageConfigQueryParam;
import com.nanyang.academy.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.nanyang.academy.service.PageConfigService;
import com.nanyang.academy.entity.PageConfig;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/api/pageConfig")
@Api(tags = "页面设置")
public class PageConfigController {
    @Autowired
    private PageConfigService pageConfigService;


    @GetMapping("/getPageConfigById/{id}")
    @ApiOperation(value = "根据id获取")
    public PageConfig get(@PathVariable Long id) {
        return pageConfigService.getById(id);
    }

    @PostMapping("/getPageConfigListPage")
    @ApiOperation(value = "分页获取列表")
    public ResultEntity<PageUtils<IPage<PageConfig>>> getPageConfigListPage(@RequestBody PageConfigQueryParam param) {
        IPage<PageConfig> list = pageConfigService.getPageConfigListPage(param);
        return ResultEntity.getOkResult(new PageUtils<>(list));
    }

    @PostMapping("/editPageConfig")
    @ApiOperation(value = "修改")
    public PageConfig update(@RequestBody PageConfig pageConfig) {
        pageConfigService.updateById(pageConfig);
        return pageConfig;
    }
    @PostMapping("/addPageConfig")
    @ApiOperation(value = "新增")
    public ResultEntity save(@RequestBody PageConfig pageConfig) {
        boolean res = pageConfigService.save(pageConfig);
        return res?ResultEntity.getOkResult():ResultEntity.getErrorResult();
    }

    @GetMapping("/getPageConfigByPosition/{position}")
    @ApiOperation(value = "根据位置获取")
    public List<PageConfig> get(@PathVariable String position) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("position",position);
        List<PageConfig> configList = pageConfigService.list(wrapper);
        return configList;
    }
}
