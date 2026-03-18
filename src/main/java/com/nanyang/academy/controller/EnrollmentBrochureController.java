package com.nanyang.academy.controller;

import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.entity.ActivityNotice;
import com.nanyang.academy.entity.EnrollmentBrochure;
import com.nanyang.academy.entity.dto.ActivityNoticeVo;
import com.nanyang.academy.entity.param.ActivityNoticeQueryParam;
import com.nanyang.academy.entity.param.EnrollmentBrochureQueryParam;
import com.nanyang.academy.service.IEnrollmentBrochureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 招生简章文件表 前端控制器
 * </p>
 *
 * @author yiyu
 * @since 2026-03-17
 */
@RestController
@RequestMapping("/api/enrollment-brochure")
@Api(tags = "招生简章")
public class EnrollmentBrochureController {
    @Autowired
    private IEnrollmentBrochureService iEnrollmentBrochureService;

    @ApiOperation("分页列表")
    @PostMapping("/getListPage")
    public ResultEntity<List<EnrollmentBrochure>> getListPage(@RequestBody EnrollmentBrochureQueryParam param) {
        return ResultEntity.getOkResult(iEnrollmentBrochureService.getEnrollmentBrochureListPage(param));
    }

    // 新增
    @ApiOperation("新增")
    @PostMapping("/add")
    public ResultEntity<Boolean> add(@RequestBody EnrollmentBrochure enrollmentBrochure) {
        return ResultEntity.getOkResult(iEnrollmentBrochureService.save(enrollmentBrochure));
    }

    // 修改
    @ApiOperation("修改")
    @PostMapping("/update")
    public ResultEntity<Boolean> update(@RequestBody EnrollmentBrochure enrollmentBrochure) {
        return ResultEntity.getOkResult(iEnrollmentBrochureService.updateById(enrollmentBrochure));
    }

    // 删除
    @ApiOperation("删除")
    @GetMapping("/delete/{id}")
    public ResultEntity<Boolean> delete(@PathVariable Long id) {
        return ResultEntity.getOkResult(iEnrollmentBrochureService.removeById(id));
    }

    // 详情
    @ApiOperation("详情")
    @GetMapping("/get/{id}")
    public ResultEntity<EnrollmentBrochure> get(@PathVariable Long id) {
        return ResultEntity.getOkResult(iEnrollmentBrochureService.getById(id));
    }
}
