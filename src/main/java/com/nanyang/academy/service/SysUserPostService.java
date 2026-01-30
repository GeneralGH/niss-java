package com.nanyang.academy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nanyang.academy.entity.SysUserPost;

public interface SysUserPostService extends IService<SysUserPost> {
    SysUserPost update(SysUserPost sysUserPost);
}
