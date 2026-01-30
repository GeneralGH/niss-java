package com.nanyang.academy.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.nanyang.academy.common.Constant;
import com.nanyang.academy.entity.SysUser;
import com.nanyang.academy.entity.pojo.LoginUser;
import com.nanyang.academy.manager.AsyncFactory;
import com.nanyang.academy.manager.AsyncManager;
import com.nanyang.academy.mapper.SysUserMapper;
import com.nanyang.academy.utils.http.IpUtils;
import com.nanyang.academy.utils.msgVerify.PhoneFormatCheckUtils;
import com.nanyang.academy.exception.*;
import com.nanyang.academy.service.*;
import com.nanyang.academy.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author pt
 * @date 2022-06-02
 */
@Component
public class SysLoginService {

    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private SysConfigService configService;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid) {
        Integer wCount = redisCache.getCacheObject(Constant.PASSWORD_WRONG_COUNT+username);

        if (ObjectUtils.isNotEmpty(wCount) && wCount >=3){
            throw new CustomException("密码输入错误次数超过3次，请联系管理员或者24小时后再试");
        }

        boolean captchaOnOff = configService.selectCaptchaOnOff();
        // 验证码开关
        if (captchaOnOff) {
            validateCaptcha(username, code, uuid);
        }
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constant.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                if (ObjectUtils.isEmpty(wCount))
                    wCount = 0 ;
                wCount ++;
                redisCache.setCacheObject(Constant.PASSWORD_WRONG_COUNT+username,wCount,24, TimeUnit.HOURS);
                if (wCount >=3){
                    SysUser user = userMapper.selectUserByUserName(username);
                    if (ObjectUtils.isNotEmpty(user)){
                        user.setStatus(1);
                        userMapper.updateUserStatus(user);
                    }
                    throw new CustomException("密码输入错误次数超过3次，请联系管理员或者24小时后再试");
                }
                throw new CustomException("密码输入错误次数"+ wCount +"次,超过3次将被锁定24小时");
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constant.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        }
        if (ObjectUtils.isNotEmpty(wCount)){
            redisCache.deleteObject(Constant.PASSWORD_WRONG_COUNT+username);
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constant.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        recordLoginInfo(loginUser.getUserId());
        //判断缓存中是否存在当前用户
        /*Collection<String> keys = redisCache.keys(Constant.LOGIN_TOKEN_KEY + "*");
        String old_token = "";
        keys.stream().forEach(key -> {
            LoginUser loginUser1 = redisCache.getCacheObject(key);
            if (loginUser1.getUser().getUserName().equals(loginUser.getUser().getUserName())){
                //redisCache.deleteObject(key);
                old_token = redisCache.getCacheObject(key);
            }
        });*/
        SysUser user = loginUser.getUser();

        loginUser.setUser(user);
        // 生成token
        return tokenService.createToken(loginUser);
    }


    public String loginSms(String username, String code, String uuid)
    {
        Integer wCount = redisCache.getCacheObject(Constant.PASSWORD_WRONG_COUNT+username);
        if (ObjectUtils.isNotEmpty(wCount) && wCount >=3){
            throw new CustomException("密码输入错误次数超过3次，请联系管理员或者24小时后再试");
        }
        validateSmsCode(username, code, uuid);

        SysUser sysUser = sysUserService.selectUserByUserName(username);
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constant.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = new LoginUser(sysUser.getUserId(), sysUser.getDeptId(), sysUser, permissionService.getMenuPermission(sysUser));
        recordLoginInfo(loginUser.getUserId());
        //判断缓存中是否存在当前用户
        /*Collection<String> keys = redisCache.keys(Constant.LOGIN_TOKEN_KEY + "*");
        keys.stream().forEach(key -> {
            LoginUser loginUser1 = redisCache.getCacheObject(key);
            if (loginUser1.getUser().getUserName().equals(loginUser.getUser().getUserName()))
                redisCache.deleteObject(key);
        });*/
        SysUser user = loginUser.getUser();

        loginUser.setUser(user);
        // 生成token
        return tokenService.createToken(loginUser);
    }

    public String loginWechat(String username, String password, String code, String uuid)
    {
        SysUser sysUser = sysUserService.selectUserByUserName(username);
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constant.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = new LoginUser(sysUser.getUserId(), sysUser.getDeptId(), sysUser, permissionService.getMenuPermission(sysUser));
        recordLoginInfo(loginUser.getUserId());

        // 生成token
        return tokenService.createToken(loginUser);
    }


    public void validateSmsCode(String username, String code, String phone)
    {
        String verifyKey = Constant.PREFIX_VERIFY_CODE + StringUtil.nvl(phone, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constant.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CustomException("验证码已失效");
        }
        if (!code.equalsIgnoreCase(captcha))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constant.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CustomException("验证码错误");
        }
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = Constant.CAPTCHA_CODE_KEY + StringUtil.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constant.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constant.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        sysUser.setLoginDate(DateUtils.getNowDate());
        sysUserService.updateUserProfile(sysUser);
    }
}
