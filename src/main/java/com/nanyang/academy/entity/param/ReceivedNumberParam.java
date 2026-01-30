package com.nanyang.academy.entity.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName pt
 * @Description TODO
 * @Author pt
 * @Date 2022/7/28
 * @Version 1.0
 **/
@Data
public class ReceivedNumberParam {
    @ApiModelProperty(value = "开始时间")
    private Date beginTime;
    @ApiModelProperty(value = "结束时间")
    private Date endTime;
    @ApiModelProperty(value = "区域")
    private String area;

    @ApiModelProperty(value = "开始时间")
    private Long begin;
    @ApiModelProperty(value = "结束时间")
    private Long end;
}
