package com.nanyang.academy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.entity.News;
import com.nanyang.academy.entity.dto.LastAndNextVo;
import com.nanyang.academy.entity.dto.NewsVo;
import com.nanyang.academy.entity.param.NewsQueryParam;
import com.nanyang.academy.entity.param.SortChangeParam;
import com.nanyang.academy.service.NewsService;
import com.nanyang.academy.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/news")
@Api(tags = "新闻")
public class NewsController extends BaseController{
    @Autowired
    private NewsService newsService;

    @PostMapping("/addNews")
    @ApiOperation(value = "保存")
    public ResultEntity save(@RequestBody News news) {
        news.setCreateBy(getUserId());
        news.setCreateTime(new Date());
        news.setSort(newsService.getLastSortBytype(news.getType())+1);
        newsService.save(news);
        return ResultEntity.getOkResult("新增成功");
    }


    @GetMapping("/getNewsById/{id}")
    @ApiOperation(value = "根据id获取")
    public ResultEntity<News> get(@PathVariable Long id) {
        return ResultEntity.getOkResult(newsService.getById(id));
    }





    @PostMapping("/getNewsListPage")
    @ApiOperation(value = "分页获取列表")
    public ResultEntity<PageUtils<IPage<NewsVo>>> getNewsListPage(@RequestBody NewsQueryParam param) {
        IPage<NewsVo> list = newsService.getNewsListPage(param);
        return ResultEntity.getOkResult(new PageUtils<>(list));
    }

    @DeleteMapping("/delNewsById/{id}")
    @ApiOperation(value = "删除")
    public ResultEntity delete(@PathVariable Long id) {
        newsService.removeById(id);
        return ResultEntity.getOkResult();
    }

    @PostMapping("/getLastAndNext")
    @ApiOperation(value = "根据id获取前后")
    public ResultEntity<LastAndNextVo> getLastAndNext(@RequestBody News news) {
        return ResultEntity.getOkResult(newsService.LastAndNextVo(news));
    }

    @PostMapping("/editNews")
    @ApiOperation(value = "修改")
    public ResultEntity update(@RequestBody News news) {
        news.setUpdateBy(getUserId());
        news.setUpdateTime(new Date());
        newsService.updateById(news);
        return ResultEntity.getOkResult();
    }

    @PostMapping("/editSort")
    @ApiOperation(value = "排序")
    public ResultEntity editSort(@RequestBody SortChangeParam param) {

        newsService.editSort(param);
        return ResultEntity.getOkResult();
    }
}
