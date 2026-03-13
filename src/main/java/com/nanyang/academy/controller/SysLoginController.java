package com.nanyang.academy.controller;

import com.nanyang.academy.annotation.Log;
import com.nanyang.academy.common.*;
import com.nanyang.academy.entity.SysMenu;
import com.nanyang.academy.entity.SysUser;
import com.nanyang.academy.entity.dto.UserLoginDTO;
import com.nanyang.academy.entity.enums.BusinessType;
import com.nanyang.academy.entity.enums.UserStatus;
import com.nanyang.academy.entity.enums.UserType;
import com.nanyang.academy.entity.pojo.LoginSmsInput;
import com.nanyang.academy.entity.pojo.LoginUser;
import com.nanyang.academy.exception.CustomException;
import com.nanyang.academy.service.impl.SysLoginService;
import com.nanyang.academy.service.impl.SysPermissionService;
import com.nanyang.academy.utils.RedisCache;
import com.nanyang.academy.utils.SecurityUtils;
import com.nanyang.academy.utils.wx.WxUtil;
import com.nanyang.academy.common.Constant;
import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * @author pt
 * @date 2022-06-01
 */
@RestController
@RequestMapping("/api")
@Api(tags = "登录")
public class SysLoginController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(SysLoginController.class);

    @Autowired
    private SysLoginService loginService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private SysMenuService menuService;


    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public ResultEntity login(@RequestBody UserLoginDTO param) {
        SysUser user = userService.selectUserByUserName(param.getUserName());
        if (com.baomidou.mybatisplus.core.toolkit.ObjectUtils.isEmpty(user)){
            throw new CustomException("对不起，账号：" + param.getUserName() + "不存在");
        }
        if (UserStatus.DISABLE.getCode().equals(user.getStatus().toString()))
        {
            log.info("登录用户：{} 已被停用.", param.getUserName());
            throw new CustomException("对不起，您的账号：" + param.getUserName() + " 已停用");
        }
        if (UserType.CUSTOMER.getCode().equals(user.getUserType()))
        {
            log.info("登录用户：{} 没有权限.", user.getUserName());
            throw new CustomException("对不起，您的账号：" + user.getUserName() + " 没有权限");
        }

        String token = loginService.login(param.getUserName(), param.getPassword(), param.getCode(),
                param.getUuid());

        Map data = new HashMap();
        data.put(Constant.TOKEN, token);
        return new ResultEntity<>(HttpStatus.SUCCESS, "登录成功", data);
    }

    /*@ApiOperation(value = "短信登录")
    @PostMapping("/loginSms")
    public ResultEntity loginSms(@Validated @RequestBody LoginSmsInput input, HttpServletResponse response) {
        SysUser param = userService.getUserByPhone(input);
        if (param == null)
            throw new CustomException("该账号不存在");
        //状态为1的账户不能登录 getStatus 0：正常 1:锁定
        if (param.getStatus() == 1)
            throw new CustomException("该账号已被锁定");
        //00系统用户  01普通用户
        if (UserType.CUSTOMER.getCode().equals(param.getUserType()))
            throw new CustomException("该账号无管理权限");
        String token = loginService.loginSms(param.getUserName(),input.getCode(),input.getPhone());

        Map data = new HashMap();
        data.put(Constant.TOKEN, token);
        return new ResultEntity<>(HttpStatus.SUCCESS, "登录成功", data);
    }
*/
    /*@Autowired
    private SysConfigService configService;*/

    /**
     * 用户注销
     */
    @Log(title = "用户注销", businessType = BusinessType.FORCE)
    @PostMapping("/logout")
    @ApiOperation("用户注销")
    public ResultEntity forceLogout() {
        SysUser user = getLoginUser().getUser();
        LoginUser loginUser = getLoginUser();
        String userKey = getTokenKey(loginUser.getToken());
        redisCache.deleteObject(userKey);
        Collection<String> keys = redisCache.keys(Constant.LOGIN_TOKEN_KEY + "*");
        keys.stream().forEach(key -> {
            LoginUser loginUser1 = redisCache.getCacheObject(key);
            if (loginUser1.getUser().getUserName().equals(loginUser.getUser().getUserName())){
                redisCache.deleteObject(key);
            }
        });
        return ResultEntity.getOkResult();
    }
    private String getTokenKey(String uuid)
    {
        return Constant.LOGIN_TOKEN_KEY + uuid;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/getInfo")
    @ApiOperation("获取用户信息")
    public ResultEntity getInfo() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        Map<String, Object> data = new HashMap<>();
        data.put("user", user);
        data.put("roles", roles);
        data.put("permissions", permissions);
        return ResultEntity.getOkResult(data);
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("/getRouters")
    @ApiOperation("获取路由信息")
    public ResultEntity getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        //List<SysMenu> menus = menuService.selectMenuList(userId);
        return ResultEntity.getOkResult(menuService.buildRouter(menus));
    }
}
