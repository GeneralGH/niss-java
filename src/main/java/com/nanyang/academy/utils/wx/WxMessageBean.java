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
public class WxMessageBean {

    private MessageBean first;

    private MessageBean keyword1;

    private MessageBean keyword2;

    private MessageBean keyword3;

    private MessageBean keyword4;

    private MessageBean remark;

    /**
     * 自定义构造，first和remark必传，keyword根据需要自定义个数
     *
     * @param first
     * @param remark
     * @param keyword
     */
    public WxMessageBean(MessageBean first, MessageBean remark, MessageBean... keyword) {
        this.first = first;
        int count = 1;
        for (MessageBean keyword1 : keyword) {
            if (count == 1) {
                this.keyword1 = keyword1;
            } else if (count == 2) {
                this.keyword2 = keyword1;
            } else if (count == 3) {
                this.keyword3 = keyword1;
            } else if (count == 4) {
                this.keyword4 = keyword1;
            }
            count++;
        }

        this.remark = remark;
    }
}
