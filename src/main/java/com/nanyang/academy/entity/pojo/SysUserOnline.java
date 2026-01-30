package com.nanyang.academy.entity.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author pt
 * @date 2022-06-09
 */
@Data
public class SysUserOnline implements Serializable {
    /** 会话编号 */
    private String tokenId;

    /** 部门名称 */
    private String deptName;

    /** 用户名称 */
    private String userName;

    /** 登录IP地址 */
    private String ipaddr;

    /** 登录地址 */
    private String loginLocation;

    /** 浏览器类型 */
    private String browser;

    /** 操作系统 */
    private String os;

    /** 登录时间 */
    private Long loginTime;
}
