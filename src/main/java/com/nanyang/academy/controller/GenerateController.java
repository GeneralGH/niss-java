package com.nanyang.academy.controller;


import com.nanyang.academy.entity.pojo.BeanField;
import com.nanyang.academy.entity.pojo.GenerateDetail;
import com.nanyang.academy.entity.pojo.GenerateInput;
import com.nanyang.academy.service.GenerateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author
 */
@RestController
@RequestMapping("/generate")
@Api(tags = "Generate")
public class GenerateController extends BaseController{
    @Autowired
    private GenerateService generateService;
    @ApiOperation(value = "获取数据库数据的方法",notes = "根据字段查询数据库表")
    @GetMapping("/value")
    public GenerateInput Value(String name, String packageName, String path){
        GenerateDetail generateDetail = new GenerateDetail();
        List<BeanField> beanFields = generateService.listBeanField(name);
        name = generateService.upperFirstChar(name);
        GenerateInput input = new GenerateInput();
        input.setTableName(name);
        input.setPackageName(packageName);
        GenerateInput generate = generateService.generate(beanFields, input);
        generate.setPackageName(packageName);
        generate.setPath(path);
        generateService.saveCode(generate);
        //generateDetail.setFields(beanFields);
        //generateDetail.setBeanName(generateService.upperFirstChar(name));
        return generate;
    }

    @ApiOperation(value = "生成代码",notes = "根据信息生成代码")
    @PostMapping("")
    public void save(GenerateInput input){
        generateService.saveCode(input);
    }
}
