package com.nanyang.academy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nanyang.academy.entity.SysRoleMenu;

public interface SysRoleMenuService extends IService<SysRoleMenu> {
    SysRoleMenu update(SysRoleMenu sysRoleMenu);
}
