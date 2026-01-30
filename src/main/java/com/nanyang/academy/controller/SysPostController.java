package com.nanyang.academy.controller;

import com.nanyang.academy.annotation.Log;
import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.common.UserConstants;
import com.nanyang.academy.entity.SysPost;
import com.nanyang.academy.entity.enums.BusinessType;
import com.nanyang.academy.exception.CustomException;
import com.nanyang.academy.service.SysPostService;
import com.nanyang.academy.utils.file.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@Api(tags = "职务岗位")
public class SysPostController  extends BaseController {
    @Autowired
    private SysPostService postService;


    /**
     * 获取岗位列表
     */
    @PreAuthorize("@ss.hasPermi('system:post:list')")
    @ApiOperation("获取岗位列表")
    @GetMapping("/list")
    public ResultEntity list(SysPost post)
    {
        //startPage();
        List<SysPost> list = postService.selectPostList(post);
        return ResultEntity.getOkResult(list);
    }

    @Log(title = "岗位管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:post:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysPost post)
    {
        List<SysPost> list = postService.selectPostList(post);
        ExcelUtil<SysPost> util = new ExcelUtil<SysPost>(SysPost.class);
        util.exportExcel(response, list, "岗位数据");
    }

    /**
     * 根据岗位编号获取详细信息
     */
    @ApiOperation("根据岗位编号获取详细信息")
    @PreAuthorize("@ss.hasPermi('system:post:query')")
    @GetMapping(value = "/getInfo/{postId}")
    public ResultEntity getInfo(@PathVariable Long postId)
    {
        return ResultEntity.getOkResult(postService.selectPostById(postId));
    }

    /**
     * 新增岗位
     */
    @PreAuthorize("@ss.hasPermi('system:post:add')")
    @ApiOperation("新增岗位")
    @Log(title = "岗位管理", businessType = BusinessType.INSERT)
    @PostMapping("/addPost")
    public ResultEntity add(@Validated @RequestBody SysPost post)
    {
        if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post)))
        {
            throw new CustomException("新增岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post)))
        {
            throw new CustomException("新增岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        post.setCreateUser(getUsername());
        return ResultEntity.getOkResult(postService.insertPost(post));
    }

    /**
     * 修改岗位
     */
    @PreAuthorize("@ss.hasPermi('system:post:edit')")
    @ApiOperation("修改岗位")
    @Log(title = "岗位管理", businessType = BusinessType.UPDATE)
    @PutMapping("/editPost")
    public ResultEntity edit(@Validated @RequestBody SysPost post)
    {
        if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post)))
        {
            throw new CustomException("修改岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post)))
        {
            throw new CustomException("修改岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        post.setUpdateUser(getUsername());
        return ResultEntity.getOkResult(postService.updatePost(post));
    }

    /**
     * 删除岗位
     */
    @PreAuthorize("@ss.hasPermi('system:post:remove')")
    @ApiOperation("删除岗位")
    @Log(title = "岗位管理", businessType = BusinessType.DELETE)
    @PutMapping("/removePost")
    public ResultEntity remove(@RequestParam Long[] postIds)
    {
        return toAjax(postService.deletePostByIds(postIds));
    }

    /**
     * 获取岗位选择框列表
     */
    @ApiOperation("获取岗位选择框列表")
    @GetMapping("/optionselect")
    public ResultEntity optionselect()
    {
        List<SysPost> posts = postService.selectPostAll();
        return ResultEntity.getOkResult(posts);
    }
}
