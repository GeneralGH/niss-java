package com.nanyang.academy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nanyang.academy.annotation.Log;
import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.entity.SysOperLog;
import com.nanyang.academy.entity.param.OperLogQueryParam;
import com.nanyang.academy.entity.enums.BusinessType;
import com.nanyang.academy.service.SysOperLogService;
import com.nanyang.academy.utils.PageUtils;
import com.nanyang.academy.utils.file.ExcelUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/sysOperLog")
@Api(tags = "sysOperLog")
public class SysOperLogController extends BaseController {
    @Autowired
    private SysOperLogService operLogService;

    @PreAuthorize("@ss.hasPermi('monitor:operlog:list')")
    @GetMapping("/list")
    public ResultEntity list(OperLogQueryParam param)
    {
        //startPage();
        IPage<SysOperLog> list = operLogService.selectOperLogListPage(param);
        return ResultEntity.getOkResult(new PageUtils<>(list));
    }

    @Log(title = "操作日志", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysOperLog operLog)
    {
        List<SysOperLog> list = operLogService.selectOperLogList(operLog);
        ExcelUtil<SysOperLog> util = new ExcelUtil<SysOperLog>(SysOperLog.class);
        util.exportExcel(response, list, "操作日志");
    }

    @Log(title = "操作日志", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
    @DeleteMapping("/{operIds}")
    public ResultEntity remove(@PathVariable Long[] operIds)
    {
        return toAjax(operLogService.deleteOperLogByIds(operIds));
    }

    @Log(title = "操作日志", businessType = BusinessType.CLEAN)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
    @DeleteMapping("/clean")
    public ResultEntity clean()
    {
        operLogService.cleanOperLog();
        return ResultEntity.getOkResult();
    }
}
