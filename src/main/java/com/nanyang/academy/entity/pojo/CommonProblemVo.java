package com.nanyang.academy.entity.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author xj
 * @title: CommonProblemVo
 * @projectName gy
 * @description: TODO
 * @date 2022/7/18 9:15
 */
@Data
public class CommonProblemVo {

    @ApiModelProperty(value = "")
    private Long id;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "创建人id")
    private Long createUser;
    @ApiModelProperty(value = "")
    private Date createTime;
    @ApiModelProperty(value = "创建人姓名")
    private String createUserName;

}
