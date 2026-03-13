package com.nanyang.academy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.entity.Banner;
import com.nanyang.academy.entity.dto.BannerVo;
import com.nanyang.academy.entity.param.BannerQueryParam;
import com.nanyang.academy.entity.param.SortChangeParam;
import com.nanyang.academy.service.BannerService;
import com.nanyang.academy.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/banner")
@Api(tags = "轮播图")
public class BannerController extends BaseController {
    @Autowired
    private BannerService bannerService;

    @PostMapping("/addBanner")
    @ApiOperation(value = "保存")
    public ResultEntity save(@RequestBody Banner banner) {
        banner.setCreateBy(getUserId());
        banner.setCreateTime(new Date());
        banner.setSort(bannerService.getLastSort()+1);
        bannerService.save(banner);
        return ResultEntity.getOkResult("新增成功");
    }


    @GetMapping("/getBannerById/{id}")
    @ApiOperation(value = "根据id获取")
    public ResultEntity<Banner> get(@PathVariable Long id) {
        return ResultEntity.getOkResult(bannerService.getById(id));
    }

    @PostMapping("/getBannerListPage")
    @ApiOperation(value = "分页获取列表")
    public ResultEntity<PageUtils<IPage<BannerVo>>> getBannerListPage(@RequestBody BannerQueryParam param) {
        IPage<BannerVo> list = bannerService.getBannerListPage(param);
        return ResultEntity.getOkResult(new PageUtils<>(list));
    }

    @DeleteMapping("/delBannerById/{id}")
    @ApiOperation(value = "删除")
    public ResultEntity delete(@PathVariable Long id) {
        bannerService.removeById(id);
        return ResultEntity.getOkResult();
    }


    @PostMapping("/editBanner")
    @ApiOperation(value = "修改")
    public ResultEntity update(@RequestBody Banner banner) {
        banner.setUpdateBy(getUserId());
        banner.setUpdateTime(new Date());

        bannerService.updateById(banner);
        return ResultEntity.getOkResult();
    }

    @PostMapping("/editSort")
    @ApiOperation(value = "排序")
    public ResultEntity editSort(@RequestBody SortChangeParam param) {
        bannerService.editSort(param);
        return ResultEntity.getOkResult();
    }
}
