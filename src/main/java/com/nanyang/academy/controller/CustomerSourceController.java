package com.nanyang.academy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.config.HengYuanConfig;
import com.nanyang.academy.entity.Customer;
import com.nanyang.academy.entity.param.SourceQueryParam;
import com.nanyang.academy.utils.ChineseUtils;
import com.nanyang.academy.utils.PageUtils;
import com.nanyang.academy.utils.file.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.nanyang.academy.service.CustomerSourceService;
import com.nanyang.academy.entity.CustomerSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/api/customerSource")
@Api(tags = "生成链接")
public class CustomerSourceController extends BaseController{
    @Autowired
    private CustomerSourceService customerSourceService;

    @PostMapping("/addSource")
    @ApiOperation(value = "保存")
    public ResultEntity save(@RequestBody CustomerSource customerSource) {
        customerSource.setCreateTime(new Date());
        customerSource.setCreateUser(getUserId());
        customerSource.setSourceKey(ChineseUtils.toPinyin(customerSource.getName()));
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("source_key",ChineseUtils.toPinyin(customerSource.getName()));
        CustomerSource source = customerSourceService.getOne(wrapper);
        if (ObjectUtils.isNotEmpty(source)){
            Integer a = (int)(Math.random()*10000);
            customerSource.setSourceKey(ChineseUtils.toPinyin(customerSource.getName()) + a);
        }
        customerSourceService.save(customerSource);

        String url = HengYuanConfig.getCustomerUrl() + "?source="+ ChineseUtils.toPinyin(customerSource.getName());

        return ResultEntity.getOkResult(url);
    }

    @PostMapping("/getSourceListPage")
    @ApiOperation(value = "保存")
    public ResultEntity<PageUtils<IPage<CustomerSource>>> getSourceListPage(@RequestBody SourceQueryParam param) {
        IPage<CustomerSource> res = customerSourceService.getSourceListPage(param);

        return ResultEntity.getOkResult(new PageUtils<>(res));
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取")
    public CustomerSource get(@PathVariable Long id) {
        return customerSourceService.getById(id);
    }

    @PostMapping("/delSource/{id}")
    @ApiOperation(value = "删除")
    public void delete(@PathVariable Long id) {
        customerSourceService.removeById(id);
    }


    @PostMapping("/editSource")
    @ApiOperation(value = "修改")
    public ResultEntity update(@RequestBody CustomerSource customerSource) {
        boolean res = customerSourceService.updateById(customerSource);
        return res?ResultEntity.getOkResult():ResultEntity.getErrorResult();
    }

    @PostMapping("/addVisitNum/{sourceKey}")
    @ApiOperation(value = "增加访问次数")
    public ResultEntity addVisitNum(@PathVariable String sourceKey) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("source_key",sourceKey);
        CustomerSource customerSource = customerSourceService.getOne(wrapper);
        if (ObjectUtils.isNotEmpty(customerSource.getVisitNum())){
            customerSource.setVisitNum(customerSource.getVisitNum() + 1);
        }else {
            customerSource.setVisitNum(1);
        }
        boolean res = customerSourceService.updateById(customerSource);
        return res?ResultEntity.getOkResult():ResultEntity.getErrorResult();
    }

    @PostMapping("/getVisitNumStatistics")
    @ApiOperation(value = "访问来源次数统计")
    public ResultEntity<List<CustomerSource>> getVisitNumStatistics() {

        List<CustomerSource> res = customerSourceService.list();

        return ResultEntity.getOkResult(res);
    }

    /**
     *
     * @param request
     * @param response
     */
    @PostMapping("/exportVisitNumStatistics")
    @ApiOperation(value = "导出咨询访问来源次数统计")
    public void exportVisitNumStatistics(HttpServletRequest request,HttpServletResponse response) {
        String[] headersName = {"name","visit_num"};
        String[] headers = {"网站名称","咨询访问来源次数"};
        List<Object[]> datas = new ArrayList<>();
        List<CustomerSource> res = customerSourceService.list();
        for (CustomerSource c:res) {
            Object[] objects = new Object[headers.length];
            objects[0]=c.getName();
            objects[1] = c.getVisitNum();
            datas.add(objects);
        }

        ExcelUtil.excelExport("咨询访问来源次数统计",headers,datas,response);

    }

}
