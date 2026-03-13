package com.nanyang.academy.entity.enums;

/**
 * @ClassName pt
 * @Description TODO
 * @Author pt
 * @Date 2022/7/21
 * @Version 1.0
 **/
public enum UserType {
    ADMIN("00", "系统用户"),
    CUSTOMER("01", "普通用户")
    ;
    private final String code;
    private final String info;

    UserType(String code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public String getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }
}
