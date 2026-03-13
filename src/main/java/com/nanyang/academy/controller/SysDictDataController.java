package com.nanyang.academy.controller;

import com.nanyang.academy.annotation.Log;
import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.entity.SysDictData;
import com.nanyang.academy.entity.enums.BusinessType;
import com.nanyang.academy.service.SysDictDataService;
import com.nanyang.academy.service.SysDictTypeService;
import com.nanyang.academy.utils.StringUtil;
import com.nanyang.academy.utils.file.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sysDictData")
@Api(tags = "字典数据")
public class SysDictDataController extends BaseController{
    @Autowired
    private SysDictDataService dictDataService;


    @Autowired
    private SysDictTypeService dictTypeService;

    //@PreAuthorize("@ss.hasPermi('system:dict:list')")
    //@GetMapping("/list")
    public ResultEntity list(SysDictData dictData)
    {
        //startPage();
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        return ResultEntity.getOkResult(list);
    }

    //@Log(title = "字典数据", businessType = BusinessType.EXPORT)
    //@PreAuthorize("@ss.hasPermi('system:dict:export')")
    //@PostMapping("/export")
    public void export(HttpServletResponse response, SysDictData dictData)
    {
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        ExcelUtil<SysDictData> util = new ExcelUtil<SysDictData>(SysDictData.class);
        util.exportExcel(response, list, "字典数据");
    }

    /**
     * 查询字典数据详细
     */
    //@PreAuthorize("@ss.hasPermi('system:dict:query')")
    //@GetMapping(value = "/{dictCode}")
    public ResultEntity getInfo(@PathVariable Long dictCode)
    {
        return ResultEntity.getOkResult(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    @ApiOperation("根据字典类型查询字典数据信息")
    public ResultEntity dictType(@PathVariable String dictType)
    {
        List<SysDictData> data = dictTypeService.selectDictDataByType(dictType);
        if (StringUtil.isNull(data))
        {
            data = new ArrayList<SysDictData>();
        }
        return ResultEntity.getOkResult(data);
    }

    /**
     * 新增字典类型
     */
    //@PreAuthorize("@ss.hasPermi('system:dict:add')")
    //@Log(title = "字典数据", businessType = BusinessType.INSERT)
    //@PostMapping
    public ResultEntity add(@Validated @RequestBody SysDictData dict)
    {
        dict.setCreateBy(getUsername());
        return ResultEntity.getOkResult(dictDataService.insertDictData(dict));
    }

    /**
     * 修改保存字典类型
     */
    //@PreAuthorize("@ss.hasPermi('system:dict:edit')")
    //@Log(title = "字典数据", businessType = BusinessType.UPDATE)
    //@PutMapping
    public ResultEntity edit(@Validated @RequestBody SysDictData dict)
    {
        dict.setUpdateBy(getUsername());
        return toAjax(dictDataService.updateDictData(dict));
    }

    /**
     * 删除字典类型
     */
    //@PreAuthorize("@ss.hasPermi('system:dict:remove')")
    //@Log(title = "字典类型", businessType = BusinessType.DELETE)
    //@DeleteMapping("/{dictCodes}")
    public ResultEntity remove(@PathVariable Long[] dictCodes)
    {
        dictDataService.deleteDictDataByIds(dictCodes);
        return success();
    }
}
