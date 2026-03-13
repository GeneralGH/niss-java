package com.nanyang.academy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nanyang.academy.entity.SysRoleMenu;
import com.nanyang.academy.mapper.SysRoleMenuMapper;
import com.nanyang.academy.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public SysRoleMenu update(SysRoleMenu sysRoleMenu){
        return null;
    }
}
