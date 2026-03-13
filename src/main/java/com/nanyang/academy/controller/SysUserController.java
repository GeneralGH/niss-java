package com.nanyang.academy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nanyang.academy.annotation.Log;
import com.nanyang.academy.common.Constant;
import com.nanyang.academy.common.HttpStatus;
import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.common.UserConstants;
import com.nanyang.academy.entity.SysConfig;
import com.nanyang.academy.entity.SysRole;
import com.nanyang.academy.entity.SysUser;
import com.nanyang.academy.entity.dto.UserModelVo;
import com.nanyang.academy.entity.param.UserQueryParam;
import com.nanyang.academy.entity.pojo.LoginUser;
import com.nanyang.academy.exception.CustomException;
import com.nanyang.academy.service.impl.TokenService;
import com.nanyang.academy.utils.PageUtils;
import com.nanyang.academy.utils.RedisCache;
import com.nanyang.academy.utils.SecurityUtils;
import com.nanyang.academy.utils.StringUtil;
import com.nanyang.academy.utils.file.ExcelUtil;
import com.nanyang.academy.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sysUser")
@Api(tags = "用户管理")
public class SysUserController extends BaseController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysConfigService configService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private TokenService tokenService;

    /**
     * 用户授权角色
     */
    //@PreAuthorize("@ss.hasPermi('system:user:update')")
    //@Log(title = "用户管理", businessType = BusinessType.GRANT)
    @ApiOperation(value = "用户授权角色")
    @PostMapping("/authRole")
    public ResultEntity insertAuthRole(Long userId, Long[] roleIds) {
        //sysUserService.checkUserDataScope(userId);
        sysUserService.insertUserAuth(userId, roleIds);
        return ResultEntity.getOkResult();
    }

    /**
     * 根据用户编号获取授权角色
     */
    //@PreAuthorize("@ss.hasPermi('system:user:query')")
    @ApiOperation(value = "根据用户编号获取授权角色")
    @GetMapping("/authRole/{userId}")
    public ResultEntity authRole(@PathVariable("userId") Long userId) {
        SysUser user = sysUserService.selectUserById(userId);
        user.setPassword("**");
        List<SysRole> roles = roleService.selectRolesByUserId(userId);
        Map ajax = new HashMap();
        ajax.put("user", user);
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        return ResultEntity.getOkResult(ajax);
    }

    /**
     * 根据用户编号获取详细信息
     */
    @GetMapping("/getUserInfo/{userId}")
    @ApiOperation(value = "根据id获取用户")
    //@PreAuthorize("@ss.hasPermi('system:user:query')")
    public ResultEntity getInfo(@PathVariable(value = "userId", required = false) Long userId) {
        //sysUserService.checkUserDataScope(userId);
        ResultEntity ajax = ResultEntity.getOkResult();
        List<SysRole> roles = roleService.selectRoleAll();
        Map res = new HashMap<>();
        res.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));

        if (StringUtil.isNotNull(userId)) {
            SysUser sysUser = sysUserService.selectUserById(userId);
            sysUser.setPassword("**");
            res.put("user", sysUser);
            res.put("roleIds", sysUser.getRoles().stream().map(SysRole::getRoleId).collect(Collectors.toList()));
        }
        ajax.setData(res);
        return ajax;
    }


    //@PreAuthorize("@ss.hasPermi('system:user:delete')")
    @PutMapping("/removeUsers")
    @ApiOperation(value = "批量删除用户")
    public ResultEntity removeUsers(@RequestParam Map param) {
        Long[] userIds = (Long[]) param.get("userIds");
        if (ArrayUtils.contains(userIds, getUserId())) {
            throw new CustomException("当前用户不能删除");
        }
        return ResultEntity.getOkResult(sysUserService.deleteUserByIds(userIds));
    }

    //@PreAuthorize("@ss.hasPermi('system:user:delete')")
    @PostMapping("/removeUser")
    @ApiOperation(value = "删除用户")
    public ResultEntity remove(@RequestBody SysUser user) {
        if (user.getUserId() == getUserId()) {
            throw new CustomException("当前用户不能删除");
        }
        return ResultEntity.getOkResult(sysUserService.deleteUserById(user.getUserId()));
    }


    //@PreAuthorize("@ss.hasPermi('system:user:update')")
    @PutMapping("/editUser")
    @ApiOperation(value = "修改用户")
    public ResultEntity edit(@RequestBody SysUser sysUser) {
        //sysUserService.checkUserAllowed(sysUser);
        //sysUserService.checkUserDataScope(sysUser.getUserId());
        if (StringUtil.isNotEmpty(sysUser.getPhone())
                && UserConstants.NOT_UNIQUE.equals(sysUserService.checkPhoneUnique(sysUser))) {
            throw new CustomException("修改用户'" + sysUser.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtil.isNotEmpty(sysUser.getEmail())
                && UserConstants.NOT_UNIQUE.equals(sysUserService.checkEmailUnique(sysUser))) {
            throw new CustomException("修改用户'" + sysUser.getUserName() + "'失败，邮箱账号已存在");
        }
        sysUser.setUpdateUser(getUsername());
        if(com.baomidou.mybatisplus.core.toolkit.ObjectUtils.isNotEmpty(sysUser.getPassword())){
            sysUser.setPassword(SecurityUtils.encryptPassword(sysUser.getPassword()));
        }
        sysUserService.updateUser(sysUser);
        return ResultEntity.getOkResult("修改成功");
    }

    //@PutMapping("/editUserMini")
    //@ApiOperation(value = "修改用户-小程序")
    public ResultEntity editUserMini(@RequestBody SysUser user) {
        LoginUser loginUser = getLoginUser();
        SysUser sysUser = loginUser.getUser();
        user.setUserName(sysUser.getUserName());
        if (StringUtil.isNotEmpty(sysUser.getPhone())
                && UserConstants.NOT_UNIQUE.equals(sysUserService.checkPhoneUnique(sysUser))) {
            throw new CustomException("修改用户'" + sysUser.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtil.isNotEmpty(sysUser.getEmail())
                && UserConstants.NOT_UNIQUE.equals(sysUserService.checkEmailUnique(sysUser))) {
            throw new CustomException("修改用户'" + sysUser.getUserName() + "'失败，邮箱账号已存在");
        }
        Long userId = getUserId();
        user.setUserId(userId);
        user.setUpdateUser(getUsername());
        if (sysUserService.editUserMini(user) > 0)
        {
            // 更新缓存用户信息
            sysUser.setNickName(user.getNickName());
            sysUser.setPhone(user.getPhone());
            sysUser.setEmail(user.getEmail());
            sysUser.setSex(user.getSex());
            tokenService.setLoginUser(loginUser);
            return ResultEntity.getOkResult();
        }
        return ResultEntity.getOkResult("修改个人信息异常，请联系管理员");
    }


    /**
     * 重置密码
     */
    @ApiOperation(value = "重置密码")
    @PutMapping("/resetPwd")
    public ResultEntity resetPwd(@RequestBody SysUser user) {
        String initPassword = configService.selectConfigByKey("sys.user.initPassword");
        //String res = sysUserService.getDefaultPassword();
        if (ObjectUtils.isEmpty(user.getPassword()))
            user.setPassword(SecurityUtils.encryptPassword(initPassword));
        //sysUserService.checkUserAllowed(user);
        //sysUserService.checkUserDataScope(user.getUserId());
        user.setUpdateUser(getUsername());
        return ResultEntity.getOkResult(sysUserService.resetPwd(user));
    }

    /**
     * 修改用户状态
     *
     * @param user
     * @return
     */
    @ApiOperation(value = "修改用户状态")
    @PutMapping("/changeStatus")
    public ResultEntity changeStatus(@RequestBody SysUser user) {
        if (user.getUserId() == getUserId()) {
            throw new CustomException("当前用户不能冻结");
        }
        user.setUpdateUser(getUsername());
        Integer res = sysUserService.updateUserStatus(user);
        return ResultEntity.getOkResult();
    }

    /**
     * 分页获取用户列表
     */
    @GetMapping("/getUserlist")
    @ApiOperation(value = "分页获取用户列表")
    public ResultEntity getUserlist(@Validated UserQueryParam param) {
        IPage<UserModelVo> list = sysUserService.selectUserListPage(param);
        return new ResultEntity<>(HttpStatus.SUCCESS, "查询成功", new PageUtils<>(list));
    }

    /**
     * 新增用户
     */
    @PostMapping("/addUser")
    @ApiOperation(value = "保存用户")
    public ResultEntity add(@Validated @RequestBody SysUser user) {
        user.setCreateUser(getUsername());
        String initPassword = configService.selectConfigByKey("sys.user.initPassword");
        user.setPassword(SecurityUtils.encryptPassword(initPassword));
        if (UserConstants.NOT_UNIQUE.equals(sysUserService.checkUserNameUnique(user.getUserName()))) {
            throw new CustomException("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtil.isNotEmpty(user.getPhone())
                && UserConstants.NOT_UNIQUE.equals(sysUserService.checkPhoneUnique(user))) {
            throw new CustomException("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtil.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(sysUserService.checkEmailUnique(user))) {
            throw new CustomException("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        return ResultEntity.getOkResult(sysUserService.insertUser(user));
    }

    /**
     * 绑定手机号
     *
     * @param param
     * @return
     */
    //@ApiOperation(value = "绑定手机号")
    //@PreAuthorize("@ss.hasPermi('system:user:bind')")
    //@PostMapping("/bindPhone")
    public ResultEntity bindPhone(@RequestBody Map<String,String> param) {
        String phone = param.get("phone");
        String code = param.get("code");
        String verifyKey = Constant.PREFIX_VERIFY_CODE + StringUtil.nvl(phone, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        if (!code.equals(captcha)){
            throw new CustomException("验证码不正确");
        }
        redisCache.deleteObject(verifyKey);
        LoginUser loginUser = getLoginUser();
        SysUser user = loginUser.getUser();

        if (StringUtil.isNotEmpty(phone)) {
            user.setPhone(phone);
            if (UserConstants.NOT_UNIQUE.equals(sysUserService.checkPhoneUnique(user))){
                throw new CustomException("绑定失败，手机号码已存在");
            }
            if (sysUserService.bindPhone(user)>0)
            {
                // 更新缓存用户
                loginUser.getUser().setPhone(phone);
                tokenService.setLoginUser(loginUser);
                Map res = new HashMap<>();
                res.put("phone",phone);
                redisCache.deleteObject(verifyKey);
                return ResultEntity.getOkResult(res);
            }else {
                throw new CustomException("绑定失败！");
            }
        }
        Map res = new HashMap<>();
        res.put("phone",phone);
        redisCache.deleteObject(verifyKey);
        return ResultEntity.getOkResult(res);
    }


    /*@ApiOperation(value = "获取默认密码")
    @GetMapping("/getDefaultPassword")*/
    public ResultEntity getDefaultPassword() {
        //String res = sysUserService.getDefaultPassword();
        String initPassword = configService.selectConfigByKey("sys.user.initPassword");
        return ResultEntity.getOkResult(initPassword);
    }

    /*@ApiOperation(value = "修改默认密码")
    @PostMapping("/editDefaultPassword")*/
    public ResultEntity editDefaultPassword(@RequestParam String pwd) {
        SysConfig config = configService.selectByKey("sys.user.initPassword");
        config.setConfigValue(pwd);
        configService.updateConfig(config);
        return ResultEntity.getOkResult();
    }
}
