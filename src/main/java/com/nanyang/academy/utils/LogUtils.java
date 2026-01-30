package com.nanyang.academy.utils;

/**
 * @author pt
 * @date 2022-05-30
 */
public class LogUtils {
    public static String getBlock(Object msg)
    {
        if (msg == null)
        {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }
}
