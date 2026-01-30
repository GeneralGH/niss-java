package com.nanyang.academy.controller;

import com.nanyang.academy.annotation.Log;
import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.common.UserConstants;
import com.nanyang.academy.config.HengYuanConfig;
import com.nanyang.academy.entity.SysUser;
import com.nanyang.academy.entity.enums.BusinessType;
import com.nanyang.academy.entity.pojo.LoginUser;
import com.nanyang.academy.exception.CustomException;
import com.nanyang.academy.service.SysUserService;
import com.nanyang.academy.service.impl.TokenService;
import com.nanyang.academy.utils.SecurityUtils;
import com.nanyang.academy.utils.StringUtil;
import com.nanyang.academy.utils.file.FileUploadUtils;
import com.nanyang.academy.utils.file.MimeTypeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 *个人信息 业务处理
 *
 * @author pt
 * @date 2022-06-09
 */
@RestController
@RequestMapping("/api/user/profile")
@Api(tags = "个人业务")
public class SysProfileController extends BaseController {
    @Autowired
    private SysUserService userService;

    @Autowired
    private TokenService tokenService;

    /**
     * 个人信息
     */
    @GetMapping("/getUserInfo")
    @ApiOperation(value = "个人信息")
    public ResultEntity profile()
    {
        LoginUser loginUser = getLoginUser();
        SysUser user = userService.selectUserById(loginUser.getUser().getUserId());
        loginUser.setUser(user);
        tokenService.setLoginUser(loginUser);
        Map ajax = new HashMap<>();
        ajax.put("user",user);
        return ResultEntity.getOkResult(ajax);
    }

    @GetMapping("/getUserInfoMini")
    @ApiOperation(value = "小程序获取昵称头像区域等信息")
    public ResultEntity getUserInfoMini()
    {
        Long userId = getUserId();
        SysUser user = userService.selectUserById(userId);
        Map ajax = new HashMap<>();
        ajax.put("nickName",user.getNickName());
        ajax.put("phone",user.getPhone());
        ajax.put("avatar",user.getAvatar());
        ajax.put("isBindSmart",user.getIsBindSmart());
        ajax.put("company",user.getCompany());
        ajax.put("project",user.getProject());
        ajax.put("property",user.getProperty());
        ajax.put("area",user.getArea());
        return ResultEntity.getOkResult(ajax);
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/editUserInfo")
    @ApiOperation(value = "修改用户个人信息")
    public ResultEntity updateProfile(@RequestBody SysUser user)
    {
        LoginUser loginUser = getLoginUser();
        SysUser sysUser = loginUser.getUser();
        user.setUserName(sysUser.getUserName());
        if (StringUtil.isNotEmpty(user.getPhone()) && !user.getPhone().equals(sysUser.getPhone())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            throw new CustomException("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        if (StringUtil.isNotEmpty(user.getEmail()) && !user.getEmail().equals(sysUser.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            throw new CustomException("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setUserId(sysUser.getUserId());
        user.setPassword(null);
        if (userService.updateUserProfile(user) > 0)
        {
            // 更新缓存用户信息
            if (StringUtil.isNotEmpty(user.getNickName()))
                sysUser.setNickName(user.getNickName());
            if (StringUtil.isNotEmpty(user.getPhone()))
                sysUser.setPhone(user.getPhone());
            if (StringUtil.isNotEmpty(user.getEmail()))
                sysUser.setEmail(user.getEmail());
            if (StringUtil.isNotEmpty(user.getAvatar()))
                sysUser.setAvatar(user.getAvatar());
            if (StringUtil.isNotEmpty(user.getCompany()))
                sysUser.setCompany(user.getCompany());
            if (StringUtil.isNotEmpty(user.getProject()))
                sysUser.setProject(user.getProject());
            if (StringUtil.isNotEmpty(user.getProperty()))
                sysUser.setProperty(user.getProperty());
            if (StringUtil.isNotEmpty(user.getArea()))
                sysUser.setArea(user.getArea());
            tokenService.setLoginUser(loginUser);
            return ResultEntity.getOkResult(user);
        }
        return ResultEntity.getErrorResult("修改个人信息异常，请联系管理员");
    }

    /**
     * 修改密码
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/updatePwd")
    @ApiOperation(value = "修改密码")
    public ResultEntity updatePwd(@RequestBody Map<String,String> param)
    {
        String oldPassword = param.get("oldPassword");
        String newPassword = param.get("newPassword");
        LoginUser loginUser = getLoginUser();
        String userName = loginUser.getUsername();
        String password = loginUser.getPassword();
        if (!SecurityUtils.matchesPassword(oldPassword, password))
        {
            throw new CustomException("修改密码失败，旧密码错误");
        }
        if (SecurityUtils.matchesPassword(newPassword, password))
        {
            throw new CustomException("新密码不能与旧密码相同");
        }
        if (userService.resetUserPwd(userName, SecurityUtils.encryptPassword(newPassword)) > 0)
        {
            // 更新缓存用户密码
            loginUser.getUser().setPassword(SecurityUtils.encryptPassword(newPassword));
            tokenService.setLoginUser(loginUser);
            return ResultEntity.getOkResult();
        }
        return ResultEntity.getErrorResult("修改密码异常，请联系管理员");
    }

    /**
     * 头像上传
     */
    /*@Log(title = "用户头像", businessType = BusinessType.UPDATE)
    @PostMapping("/avatar")
    @ApiOperation(value = "头像上传")*/
    public ResultEntity avatar(@RequestParam("avatarfile") MultipartFile file) throws Exception
    {
        if (!file.isEmpty())
        {
            LoginUser loginUser = getLoginUser();
            String avatar = FileUploadUtils.upload(HengYuanConfig.getAvatarPath(), file,
                    MimeTypeUtils.IMAGE_EXTENSION);
            if (userService.updateUserAvatar(loginUser.getUsername(), avatar))
            {
                Map ajax = new HashMap();
                ajax.put("avatar", avatar);
                // 更新缓存用户头像
                loginUser.getUser().setAvatar(avatar);
                tokenService.setLoginUser(loginUser);
                return ResultEntity.getOkResult(ajax);
            }
        }
        return ResultEntity.getErrorResult("上传图片异常，请联系管理员");
    }

    @Log(title = "用户头像", businessType = BusinessType.UPDATE)
    @PostMapping("/avatar")
    @ApiOperation(value = "头像上传")
    public ResultEntity avatarUpdate(@RequestParam("avatar") String avatar) throws Exception
    {
        LoginUser loginUser = getLoginUser();
        if (userService.updateUserAvatar(loginUser.getUsername(), avatar))
        {
            // 更新缓存用户头像
            loginUser.getUser().setAvatar(avatar);
            tokenService.setLoginUser(loginUser);
            return ResultEntity.getOkResult();
        }
        return ResultEntity.getErrorResult("修改头像异常，请联系管理员");
    }
}
