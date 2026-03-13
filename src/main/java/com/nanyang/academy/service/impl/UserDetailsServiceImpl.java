package com.nanyang.academy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nanyang.academy.entity.SysRole;
import com.nanyang.academy.entity.SysUser;
import com.nanyang.academy.entity.enums.UserStatus;
import com.nanyang.academy.entity.pojo.LoginUser;
import com.nanyang.academy.exception.CustomException;
import com.nanyang.academy.mapper.SysMenuMapper;
import com.nanyang.academy.mapper.SysRoleMapper;
import com.nanyang.academy.mapper.SysUserMapper;
import com.nanyang.academy.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author pt
 * @date 2022-05-30
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private SysUserMapper sysUserMapper;


    @Autowired
    private SysPermissionService permissionService;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",username);
        SysUser user = sysUserMapper.login(wrapper);
        List<SysRole> roleList = sysRoleMapper.selectRolePermissionByUserId(user.getUserId());
        user.setRoles(roleList);
        if (StringUtil.isNull(user))
        {
            log.info("登录用户：{} 不存在.", username);
            throw new CustomException("登录用户：" + username + " 不存在");
        }
        else if (UserStatus.DELETED.getCode().equals(user.getIsDelete().toString()))
        {
            log.info("登录用户：{} 已被删除.", username);
            throw new CustomException("对不起，您的账号：" + username + " 已被删除");
        }
        else if (UserStatus.DISABLE.getCode().equals(user.getStatus().toString()))
        {
            log.info("登录用户：{} 已被停用.", username);
            throw new CustomException("对不起，您的账号：" + username + " 已停用");
        }
        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user)
    {
        return new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
    }
}
