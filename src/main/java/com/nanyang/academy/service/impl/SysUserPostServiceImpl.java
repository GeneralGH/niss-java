package com.nanyang.academy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nanyang.academy.entity.SysUserPost;
import com.nanyang.academy.mapper.SysUserPostMapper;
import com.nanyang.academy.service.SysUserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserPostServiceImpl extends ServiceImpl<SysUserPostMapper, SysUserPost> implements SysUserPostService {

    @Autowired
    private SysUserPostMapper sysUserPostMapper;

    @Override
    public SysUserPost update(SysUserPost sysUserPost){
        return null;
    }
}
