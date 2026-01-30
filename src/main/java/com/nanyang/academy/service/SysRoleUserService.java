package com.nanyang.academy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nanyang.academy.entity.SysRoleUser;

public interface SysRoleUserService extends IService<SysRoleUser> {
    SysRoleUser update(SysRoleUser sysRoleUser);
}
