package com.nanyang.academy.utils.msgVerify;

import java.util.Random;

/**
 * @ClassName RandomUtil
 * @Description TODO
 * @Author pt
 * @Date 2022/7/18
 * @Version 1.0
 **/
public class RandomUtil {
    /**
     * 获取随机数
     * @author pt
     * @date 10:38 2022/7/18
     * @param
     * @return java.lang.String
     **/
    public static String getNumber() {
        return String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
    }
}
