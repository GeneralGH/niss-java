package com.nanyang.academy.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.entity.Promotion;
import com.nanyang.academy.entity.dto.PromotionLastAndNextVo;
import com.nanyang.academy.entity.dto.PromotionVo;
import com.nanyang.academy.entity.param.PromotionQueryParam;
import com.nanyang.academy.entity.param.SortChangeParam;
import com.nanyang.academy.service.PromotionService;
import com.nanyang.academy.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/promotion")
@Api(tags = "宣传大使")
public class PromotionController extends BaseController{
    @Autowired
    private PromotionService PromotionService;

    @PostMapping("/addPromotion")
    @ApiOperation(value = "保存")
    public ResultEntity save(@RequestBody Promotion Promotion) {
        Promotion.setCreateTime(new Date());
        Promotion.setCreateBy(getUserId());
        Promotion.setSort(PromotionService.getLastSort()+1);
        boolean res = PromotionService.save(Promotion);
        return ResultEntity.getOkResult(res);
    }

    @PostMapping("/getPromotionListPage")
    @ApiOperation(value = "分页获取列表")
    public ResultEntity<PageUtils<IPage<PromotionVo>>> getPromotionListPage(@RequestBody PromotionQueryParam param) {
        IPage<PromotionVo> list = PromotionService.getPromotionListPage(param);
        return ResultEntity.getOkResult(new PageUtils<>(list));
    }

    @GetMapping("/getPromotionById/{id}")
    @ApiOperation(value = "根据id获取")
    public ResultEntity get(@PathVariable Long id) {
        return ResultEntity.getOkResult(PromotionService.getById(id));
    }

    @DeleteMapping("/delPromotionById/{id}")
    @ApiOperation(value = "删除")
    public ResultEntity delete(@PathVariable Long id) {
        boolean res = PromotionService.removeById(id);
        return ResultEntity.getOkResult(res);
    }


    @PutMapping("/editPromotion")
    @ApiOperation(value = "修改")
    public ResultEntity update(@RequestBody Promotion Promotion) {
        Promotion.setUpdateBy(getUserId());
        Promotion.setUpdateTime(new Date());
        boolean res = PromotionService.updateById(Promotion);
        return ResultEntity.getOkResult(res);
    }

    @PostMapping("/editSort")
    @ApiOperation(value = "排序")
    public ResultEntity editSort(@RequestBody SortChangeParam param) {
        PromotionService.editSort(param);
        return ResultEntity.getOkResult();
    }

    @PostMapping("/getLastAndNext/{id}")
    @ApiOperation(value = "根据id获取前后")
    public ResultEntity<PromotionLastAndNextVo> getLastAndNext(@PathVariable Long id) {
        return ResultEntity.getOkResult(PromotionService.LastAndNextVo(id));
    }
}
