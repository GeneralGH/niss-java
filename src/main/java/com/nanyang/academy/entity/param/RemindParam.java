package com.nanyang.academy.entity.param;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName pt
 * @Description TODO
 * @Author pt
 * @Date 2023/5/26
 * @Version 1.0
 **/
@Data
@ApiModel(value="RemindParam", description="RemindParam")
public class RemindParam {
    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "是否开启声音提醒 0不开启  1开启")
    private Integer isRemind;

    @ApiModelProperty(value = "铃声URL")
    private String remindUrl;

}
