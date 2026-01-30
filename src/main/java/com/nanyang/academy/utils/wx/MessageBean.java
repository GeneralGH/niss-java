package com.nanyang.academy.utils.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName pt
 * @Description TODO
 * @Author pt
 * @Date 2023/4/11
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageBean {
    /**
     * 消息内容
     */
    private String value;
    /**
     * color
     */
    private String color;
}
