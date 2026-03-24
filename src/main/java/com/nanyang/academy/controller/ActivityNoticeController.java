package com.nanyang.academy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.entity.ActivityNotice;
import com.nanyang.academy.entity.dto.ActivityNoticeVo;
import com.nanyang.academy.entity.param.ActivityNoticeQueryParam;
import com.nanyang.academy.service.ActivityNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 活动预告表 前端控制器
 * </p>
 *
 * @author yiyu
 * @since 2026-03-17
 */
@RestController
@RequestMapping("/api/activity-notice")
@Api(tags = "活动预告")
public class ActivityNoticeController extends BaseController {

    @Autowired
    private ActivityNoticeService activityNoticeService;

    @ApiOperation("分页列表")
    @PostMapping("/getListPage")
    public ResultEntity<IPage<ActivityNotice>> getListPage(@RequestBody ActivityNoticeQueryParam param) {
        return ResultEntity.getOkResult(activityNoticeService.getActivityNoticeListPage(param));
    }

    // 新增
    @ApiOperation("新增")
    @PostMapping("/add")
    public ResultEntity<Boolean> add(@RequestBody ActivityNotice activityNotice) {
        return ResultEntity.getOkResult(activityNoticeService.save(activityNotice));
    }

    // 修改
    @ApiOperation("修改")
    @PostMapping("/update")
    public ResultEntity<Boolean> update(@RequestBody ActivityNotice activityNotice) {
        return ResultEntity.getOkResult(activityNoticeService.updateById(activityNotice));
    }

    // 删除
    @ApiOperation("删除")
    @GetMapping("/delete/{id}")
    public ResultEntity<Boolean> delete(@PathVariable Long id) {
        return ResultEntity.getOkResult(activityNoticeService.removeById(id));
    }

    // 详情
    @ApiOperation("详情")
    @GetMapping("/get/{id}")
    public ResultEntity<ActivityNotice> get(@PathVariable Long id) {
        return ResultEntity.getOkResult(activityNoticeService.getById(id));
    }
}
