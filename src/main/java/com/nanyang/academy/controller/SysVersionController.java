package com.nanyang.academy.controller;

import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.config.HengYuanConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName pt
 * @Description TODO
 * @Author pt
 * @Date 2022/7/27
 * @Version 1.0
 **/
/*@RestController
@RequestMapping("/api/version")
@Api(tags = "版本")*/
public class SysVersionController extends BaseController{

    @Autowired
    private HengYuanConfig hengYuanConfig;
    @GetMapping("/getVersion")
    @ApiOperation(value = "获取版本号")
    public ResultEntity getVersion(){
        String version = hengYuanConfig.getVersion();
        Map<String,String> data = new HashMap<>();
        data.put("version",version);
        return ResultEntity.getOkResult(data);
    }
}
