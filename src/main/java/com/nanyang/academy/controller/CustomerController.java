package com.nanyang.academy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.config.HengYuanConfig;
import com.nanyang.academy.entity.Customer;
import com.nanyang.academy.entity.CustomerSource;
import com.nanyang.academy.entity.dto.CustomerVo;
import com.nanyang.academy.entity.param.CustomerQueryParam;
import com.nanyang.academy.utils.PageUtils;
import com.nanyang.academy.utils.file.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.nanyang.academy.service.CustomerService;
import com.nanyang.academy.entity.Customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customer")
@Api(tags = "customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/addCustomer/{source}")
    @ApiOperation(value = "生成链接")
    public ResultEntity save(@PathVariable String source) {
        String url = HengYuanConfig.getCustomerUrl() + "?source="+source;
        return ResultEntity.getOkResult(url);
    }

    @PostMapping("/addCustomer")
    @ApiOperation(value = "保存")
    public ResultEntity save(@RequestBody Customer Customer) {
        Customer.setCreateTime(new Date());
        customerService.save(Customer);
        return ResultEntity.getOkResult("新增成功");
    }


    @GetMapping("/getCustomerById/{id}")
    @ApiOperation(value = "根据id获取")
    public ResultEntity<Customer> get(@PathVariable Long id) {
        return ResultEntity.getOkResult(customerService.getById(id));
    }

    @PostMapping("/getCustomerListPage")
    @ApiOperation(value = "分页获取列表")
    public ResultEntity<PageUtils<IPage<Customer>>> getCustomerListPage(@RequestBody CustomerQueryParam param) {
        IPage<Customer> list = customerService.getCustomerListPage(param);
        return ResultEntity.getOkResult(new PageUtils<>(list));
    }

    @DeleteMapping("/delCustomerById/{id}")
    @ApiOperation(value = "删除")
    public ResultEntity delete(@PathVariable Long id) {
        customerService.removeById(id);
        return ResultEntity.getOkResult();
    }


    @PostMapping("/editCustomer")
    @ApiOperation(value = "修改")
    public ResultEntity update(@RequestBody Customer Customer) {
        customerService.updateById(Customer);
        return ResultEntity.getOkResult();
    }



    @PostMapping("/getPieData")
    @ApiOperation(value = "获取来源统计数据")
    public ResultEntity<List<Map<String, Object>> > getPieData() {//@RequestBody CustomerQueryParam param
        List<Map<String, Object>>  res = customerService.getPieData();
        return ResultEntity.getOkResult(res);
    }

    @PostMapping("/exportVisitNumStatistics")
    @ApiOperation(value = "导出咨询次数统计")
    public void exportVisitNumStatistics(HttpServletRequest request, HttpServletResponse response) {
        String[] headersName = {"name","visit_num"};
        String[] headers = {"网站名称","咨询次数"};
        List<Object[]> datas = new ArrayList<>();
        List<Map<String, Object>>  res = customerService.getPieData();
        for (Map<String, Object> m:res) {
            Object[] objects = new Object[headers.length];
            objects[0]=m.get("name");
            objects[1] = m.get("value");
            datas.add(objects);
        }

        ExcelUtil.excelExport("咨询访问次数统计",headers,datas,response);

    }
}
