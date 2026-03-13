package com.nanyang.academy.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.nanyang.academy.service.UniqueVisitorService;
import com.nanyang.academy.entity.UniqueVisitor;

@RestController
@RequestMapping("/api/uniqueVisitor")
@Api(tags = "uniqueVisitor")
public class UniqueVisitorController {
    @Autowired
    private UniqueVisitorService uniqueVisitorService;

    @PostMapping
    @ApiOperation(value = "保存")
    public UniqueVisitor save(@RequestBody UniqueVisitor uniqueVisitor) {
        uniqueVisitorService.save(uniqueVisitor);
        return uniqueVisitor;
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取")
    public UniqueVisitor get(@PathVariable Long id) {
        return uniqueVisitorService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    public void delete(@PathVariable Long id) {
        uniqueVisitorService.removeById(id);
    }


    @PutMapping
    @ApiOperation(value = "修改")
    public UniqueVisitor update(@RequestBody UniqueVisitor uniqueVisitor) {
        uniqueVisitorService.update();
        return uniqueVisitor;
    }
}
