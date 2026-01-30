package com.nanyang.academy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nanyang.academy.entity.SysRoleDept;
import com.nanyang.academy.mapper.SysRoleDeptMapper;
import com.nanyang.academy.service.SysRoleDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleDeptServiceImpl extends ServiceImpl<SysRoleDeptMapper, SysRoleDept> implements SysRoleDeptService {

    @Autowired
    private SysRoleDeptMapper sysRoleDeptMapper;

    @Override
    public SysRoleDept update(SysRoleDept sysRoleDept){
        return null;
    }
}
