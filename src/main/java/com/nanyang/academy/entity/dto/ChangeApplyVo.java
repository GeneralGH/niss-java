package com.nanyang.academy.entity.dto;

import lombok.Data;

/**
 * @ClassName pt
 * @Description TODO
 * @Author pt
 * @Date 2023/4/17
 * @Version 1.0
 **/
@Data
public class ChangeApplyVo {

    private String nickName;

    private Integer id;

    private String avatar;

    private String customer;

    private String oldService;

    private String oldServiceName;
}
