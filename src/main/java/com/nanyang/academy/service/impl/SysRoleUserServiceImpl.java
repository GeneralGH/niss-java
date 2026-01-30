package com.nanyang.academy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nanyang.academy.entity.SysRoleUser;
import com.nanyang.academy.mapper.SysRoleUserMapper;
import com.nanyang.academy.service.SysRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleUserServiceImpl extends ServiceImpl<SysRoleUserMapper, SysRoleUser> implements SysRoleUserService {

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Override
    public SysRoleUser update(SysRoleUser sysRoleUser){
        return null;
    }
}
