package com.nanyang.academy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.entity.Course;
import com.nanyang.academy.entity.dto.CourseVo;
import com.nanyang.academy.entity.param.CourseQueryParam;
import com.nanyang.academy.entity.param.SortChangeParam;
import com.nanyang.academy.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.nanyang.academy.service.CourseService;
import com.nanyang.academy.entity.Course;

import java.util.Date;

@RestController
@RequestMapping("/api/course")
@Api(tags = "课程")
public class CourseController extends BaseController{
    @Autowired
    private CourseService courseService;
    
    


    @GetMapping("/getCourseById/{id}")
    @ApiOperation(value = "根据id获取")
    public ResultEntity<Course> get(@PathVariable Long id) {
        return ResultEntity.getOkResult(courseService.getById(id));
    }


    @PutMapping("/editCourse")
    @ApiOperation(value = "修改")
    public ResultEntity update(@RequestBody Course course) {
        course.setUpdateBy(getUserId());
        course.setUpdateTime(new Date());
        boolean res = courseService.updateById(course);
        return ResultEntity.getOkResult(res);
    }

    @PostMapping("/getCourseListPage")
    @ApiOperation(value = "分页获取列表")
    public ResultEntity<PageUtils<IPage<CourseVo>>> getCourseListPage(@RequestBody CourseQueryParam param) {
        IPage<CourseVo> list = courseService.getCourseListPage(param);
        return ResultEntity.getOkResult(new PageUtils<>(list));
    }

    @DeleteMapping("/delCourseById/{id}")
    @ApiOperation(value = "删除")
    public ResultEntity delete(@PathVariable Long id) {
        courseService.removeById(id);
        return ResultEntity.getOkResult();
    }

    @PostMapping("/addCourse")
    @ApiOperation(value = "保存")
    public ResultEntity save(@RequestBody Course Course) {
        Course.setCreateBy(getUserId());
        Course.setCreateTime(new Date());
        Course.setSort(courseService.getLastSort()+1);
        boolean res = courseService.save(Course);
        return ResultEntity.getOkResult("新增成功");
    }

    @PostMapping("/editSort")
    @ApiOperation(value = "排序")
    public ResultEntity editSort(@RequestBody SortChangeParam param) {
        courseService.editSort(param);
        return ResultEntity.getOkResult();
    }

}
