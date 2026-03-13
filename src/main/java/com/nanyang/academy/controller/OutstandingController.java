package com.nanyang.academy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.entity.Outstanding;
import com.nanyang.academy.entity.dto.LastAndNextVo;
import com.nanyang.academy.entity.dto.OutstandingLastAndNextVo;
import com.nanyang.academy.entity.dto.OutstandingVo;
import com.nanyang.academy.entity.param.OutstandingQueryParam;
import com.nanyang.academy.entity.param.SortChangeParam;
import com.nanyang.academy.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.nanyang.academy.service.OutstandingService;
import com.nanyang.academy.entity.Outstanding;

import java.util.Date;

@RestController
@RequestMapping("/outstanding")
@Api(tags = "杰出校友")
public class OutstandingController extends BaseController{
    @Autowired
    private OutstandingService outstandingService;

    @PostMapping("/addOutstanding")
    @ApiOperation(value = "保存")
    public ResultEntity save(@RequestBody Outstanding Outstanding) {
        Outstanding.setCreateTime(new Date());
        Outstanding.setCreateBy(getUserId());
        Outstanding.setSort(outstandingService.getLastSort()+1);
        boolean res = outstandingService.save(Outstanding);
        return ResultEntity.getOkResult(res);
    }

    @PostMapping("/getOutstandingListPage")
    @ApiOperation(value = "分页获取列表")
    public ResultEntity<PageUtils<IPage<OutstandingVo>>> getOutstandingListPage(@RequestBody OutstandingQueryParam param) {
        IPage<OutstandingVo> list = outstandingService.getOutstandingListPage(param);
        return ResultEntity.getOkResult(new PageUtils<>(list));
    }

    @GetMapping("/getOutstandingById/{id}")
    @ApiOperation(value = "根据id获取")
    public ResultEntity get(@PathVariable Long id) {
        return ResultEntity.getOkResult(outstandingService.getById(id));
    }

    @DeleteMapping("/delOutstandingById/{id}")
    @ApiOperation(value = "删除")
    public ResultEntity delete(@PathVariable Long id) {
        boolean res = outstandingService.removeById(id);
        return ResultEntity.getOkResult(res);
    }


    @PutMapping("/editOutstanding")
    @ApiOperation(value = "修改")
    public ResultEntity update(@RequestBody Outstanding Outstanding) {
        Outstanding.setUpdateBy(getUserId());
        Outstanding.setUpdateTime(new Date());
        boolean res = outstandingService.updateById(Outstanding);
        return ResultEntity.getOkResult(res);
    }

    @PostMapping("/editSort")
    @ApiOperation(value = "排序")
    public ResultEntity editSort(@RequestBody SortChangeParam param) {
        outstandingService.editSort(param);
        return ResultEntity.getOkResult();
    }

    @PostMapping("/getLastAndNext/{id}")
    @ApiOperation(value = "根据id获取前后")
    public ResultEntity<OutstandingLastAndNextVo> getLastAndNext(@PathVariable Long id) {
        return ResultEntity.getOkResult(outstandingService.LastAndNextVo(id));
    }
}
