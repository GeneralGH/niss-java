package com.nanyang.academy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.entity.ProjectAdmission;
import com.nanyang.academy.entity.param.ProjectAdmissionQueryParam;
import com.nanyang.academy.entity.dto.ProjectAdmissionVo;
import com.nanyang.academy.service.ProjectAdmissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/api/project/admission")
@Api(tags = "项目与招生")
public class ProjectAdmissionController {

    @Resource
    private ProjectAdmissionService projectAdmissionService;

    // 分页列表查询
    @ApiOperation("分页列表")
    @PostMapping("/getListPage")
    public ResultEntity<IPage<ProjectAdmissionVo>> getListPage(@RequestBody ProjectAdmissionQueryParam param) {
        return ResultEntity.getOkResult(projectAdmissionService.getProjectAdmissionListPage(param));
    }

    // 新增
    @ApiOperation("新增")
    @PostMapping("/add")
    public ResultEntity<Boolean> add(@RequestBody ProjectAdmission projectAdmission) {
        return ResultEntity.getOkResult(projectAdmissionService.save(projectAdmission));
    }

    // 修改
    @ApiOperation("修改")
    @PostMapping("/update")
    public ResultEntity<Boolean> update(@RequestBody ProjectAdmission projectAdmission) {
        return ResultEntity.getOkResult(projectAdmissionService.updateById(projectAdmission));
    }

    // 删除
    @ApiOperation("删除")
    @GetMapping("/delete/{id}")
    public ResultEntity<Boolean> delete(@PathVariable Long id) {
        return ResultEntity.getOkResult(projectAdmissionService.removeById(id));
    }

    // 详情
    @ApiOperation("详情")
    @GetMapping("/get/{id}")
    public ResultEntity<ProjectAdmission> get(@PathVariable Long id) {
        return ResultEntity.getOkResult(projectAdmissionService.getById(id));
    }
}