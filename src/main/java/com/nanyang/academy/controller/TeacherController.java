package com.nanyang.academy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.entity.Teacher;
import com.nanyang.academy.entity.dto.TeacherVo;
import com.nanyang.academy.entity.param.SortChangeParam;
import com.nanyang.academy.entity.param.TeacherQueryParam;
import com.nanyang.academy.utils.ChineseUtils;
import com.nanyang.academy.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.nanyang.academy.service.TeacherService;
import com.nanyang.academy.entity.Teacher;

import java.util.Date;

@RestController
@RequestMapping("/api/teacher")
@Api(tags = "教师")
public class TeacherController extends BaseController{
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/addTeacher")
    @ApiOperation(value = "保存")
    public ResultEntity save(@RequestBody Teacher teacher) {
        teacher.setCreateBy(getUserId());
        teacher.setCreateTime(new Date());
        teacher.setPinyin(ChineseUtils.toPinyin(teacher.getName()));
        teacher.setSort(teacherService.getLastSort()+1);
        teacherService.save(teacher);
        return ResultEntity.getOkResult("新增成功");
    }


    @GetMapping("/getTeacherById/{id}")
    @ApiOperation(value = "根据id获取")
    public ResultEntity<Teacher> get(@PathVariable Long id) {
        return ResultEntity.getOkResult(teacherService.getById(id));
    }

    @PostMapping("/getTeacherListPage")
    @ApiOperation(value = "分页获取列表")
    public ResultEntity<PageUtils<IPage<TeacherVo>>> getTeacherListPage(@RequestBody TeacherQueryParam param) {
        IPage<TeacherVo> list = teacherService.getTeacherListPage(param);
        return ResultEntity.getOkResult(new PageUtils<>(list));
    }

    @DeleteMapping("/delTeacherById/{id}")
    @ApiOperation(value = "删除")
    public ResultEntity delete(@PathVariable Long id) {
        teacherService.removeById(id);
        return ResultEntity.getOkResult();
    }


    @PostMapping("/editTeacher")
    @ApiOperation(value = "修改")
    public ResultEntity update(@RequestBody Teacher Teacher) {
        Teacher.setUpdateBy(getUserId());
        Teacher.setUpdateTime(new Date());
        teacherService.updateById(Teacher);
        return ResultEntity.getOkResult();
    }

    @PostMapping("/editSort")
    @ApiOperation(value = "排序")
    public ResultEntity editSort(@RequestBody SortChangeParam param) {
        teacherService.editSort(param);
        return ResultEntity.getOkResult();
    }
}
