package com.nanyang.academy.controller;

import com.nanyang.academy.common.HttpStatus;
import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.entity.dto.RegisterBody;
import com.nanyang.academy.service.SysConfigService;
import com.nanyang.academy.service.impl.SysRegisterService;
import com.nanyang.academy.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pt
 * @date 2022-06-02
 */
@RestController
@RequestMapping("/api/regist")
@Api(tags = "注册")
public class SysRegisterController extends BaseController{
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private SysConfigService configService;

    @PostMapping("/register")
    @ApiOperation("用户注册")
    public ResultEntity register(@RequestBody RegisterBody user)
    {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))))
        {
            return new ResultEntity<>(HttpStatus.ERROR,"当前系统没有开启注册功能！");
        }
        String msg = registerService.register(user);
        return StringUtil.isEmpty(msg) ? new ResultEntity<>(HttpStatus.SUCCESS,"注册成功") : new ResultEntity<>(HttpStatus.ERROR,msg);
    }
}
