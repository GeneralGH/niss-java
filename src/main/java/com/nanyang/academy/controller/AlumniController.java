package com.nanyang.academy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.entity.dto.AlumniVo;
import com.nanyang.academy.entity.param.AlumniQueryParam;
import com.nanyang.academy.entity.param.SortChangeParam;
import com.nanyang.academy.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.nanyang.academy.service.AlumniService;
import com.nanyang.academy.entity.Alumni;

import java.util.Date;

@RestController
@RequestMapping("/api/alumni")
@Api(tags = "校友寄语")
public class AlumniController extends BaseController {
    @Autowired
    private AlumniService alumniService;

    @PostMapping("/addAlumni")
    @ApiOperation(value = "保存")
    public ResultEntity save(@RequestBody Alumni alumni) {
        alumni.setCreateTime(new Date());
        alumni.setCreateBy(getUserId());
        alumni.setSort(alumniService.getLastSort()+1);
        boolean res = alumniService.save(alumni);
        return ResultEntity.getOkResult(res);
    }

    @PostMapping("/getAlumniListPage")
    @ApiOperation(value = "分页获取列表")
    public ResultEntity<PageUtils<IPage<AlumniVo>>> getAlumniListPage(@RequestBody AlumniQueryParam param) {
        IPage<AlumniVo> list = alumniService.getAlumniListPage(param);
        return ResultEntity.getOkResult(new PageUtils<>(list));
    }

    @GetMapping("/getAlumniById/{id}")
    @ApiOperation(value = "根据id获取")
    public ResultEntity<Alumni> get(@PathVariable Long id) {
        return ResultEntity.getOkResult(alumniService.getById(id));
    }

    @DeleteMapping("/delAlumniById/{id}")
    @ApiOperation(value = "删除")
    public ResultEntity delete(@PathVariable Long id) {
        boolean res = alumniService.removeById(id);
        return ResultEntity.getOkResult(res);
    }


    @PutMapping("/editAlumni")
    @ApiOperation(value = "修改")
    public ResultEntity update(@RequestBody Alumni alumni) {
        alumni.setUpdateBy(getUserId());
        alumni.setUpdateTime(new Date());

        boolean res = alumniService.updateById(alumni);
        return ResultEntity.getOkResult(res);
    }

    @PostMapping("/editSort")
    @ApiOperation(value = "排序")
    public ResultEntity editSort(@RequestBody SortChangeParam param) {
        alumniService.editSort(param);
        return ResultEntity.getOkResult();
    }
}
