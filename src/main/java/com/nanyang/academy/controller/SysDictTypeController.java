package com.nanyang.academy.controller;

import com.nanyang.academy.annotation.Log;
import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.common.UserConstants;
import com.nanyang.academy.entity.SysDictType;
import com.nanyang.academy.entity.enums.BusinessType;
import com.nanyang.academy.exception.CustomException;
import com.nanyang.academy.service.SysDictTypeService;
import com.nanyang.academy.utils.SecurityUtils;
import com.nanyang.academy.utils.file.ExcelUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/sysDictType")
@Api(tags = "sysDictType")
public class SysDictTypeController extends BaseController{
    @Autowired
    private SysDictTypeService dictTypeService;


    //@PreAuthorize("@ss.hasPermi('system:dict:list')")
    @GetMapping("/list")
    public ResultEntity list(SysDictType dictType)
    {
        //startPage();
        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
        return ResultEntity.getOkResult(list);
    }

    //@Log(title = "字典类型", businessType = BusinessType.EXPORT)
    //@PreAuthorize("@ss.hasPermi('system:dict:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysDictType dictType)
    {
        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
        ExcelUtil<SysDictType> util = new ExcelUtil<SysDictType>(SysDictType.class);
        util.exportExcel(response, list, "字典类型");
    }

    /**
     * 查询字典类型详细
     */
    //@PreAuthorize("@ss.hasPermi('system:dict:query')")
    @GetMapping(value = "/{dictId}")
    public ResultEntity getInfo(@PathVariable Long dictId)
    {
        return ResultEntity.getOkResult(dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * 新增字典类型
     */
    //@PreAuthorize("@ss.hasPermi('system:dict:add')")
    //@Log(title = "字典类型", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultEntity add(@Validated @RequestBody SysDictType dict)
    {
        if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict)))
        {
            throw new CustomException("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setCreateBy(SecurityUtils.getUsername());
        return toAjax(dictTypeService.insertDictType(dict));
    }

    /**
     * 修改字典类型
     */
    //@PreAuthorize("@ss.hasPermi('system:dict:edit')")
    //@Log(title = "字典类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultEntity edit(@Validated @RequestBody SysDictType dict)
    {
        if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict)))
        {
            throw new CustomException("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(dictTypeService.updateDictType(dict));
    }

    /**
     * 删除字典类型
     */
    //@PreAuthorize("@ss.hasPermi('system:dict:remove')")
    //@Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictIds}")
    public ResultEntity remove(@PathVariable Long[] dictIds)
    {
        dictTypeService.deleteDictTypeByIds(dictIds);
        return success();
    }

    /**
     * 刷新字典缓存
     */
    //@PreAuthorize("@ss.hasPermi('system:dict:remove')")
    //@Log(title = "字典类型", businessType = BusinessType.CLEAN)
    @DeleteMapping("/refreshCache")
    public ResultEntity refreshCache()
    {
        dictTypeService.resetDictCache();
        return ResultEntity.getOkResult();
    }

    /**
     * 获取字典选择框列表
     */
    @GetMapping("/optionselect")
    public ResultEntity optionselect()
    {
        List<SysDictType> dictTypes = dictTypeService.selectDictTypeAll();
        return ResultEntity.getOkResult(dictTypes);
    }
}
