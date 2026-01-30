package com.nanyang.academy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nanyang.academy.entity.SysRoleDept;

public interface SysRoleDeptService extends IService<SysRoleDept> {
    SysRoleDept update(SysRoleDept sysRoleDept);
}
