package com.nanyang.academy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value="Promotion", description="Promotion")
public class Promotion implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Long id;
    @ApiModelProperty(value = "标题 ")
    private String title;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "简介")
    private String introduction;
    @ApiModelProperty(value = "图片")
    private String image;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "")
    private Long createBy;
    @ApiModelProperty(value = "")
    private Date createTime;
    @ApiModelProperty(value = "")
    private Long updateBy;
    @ApiModelProperty(value = "")
    private Date updateTime;
    @ApiModelProperty(value = "标题-英")
    private String titleEn;
    @ApiModelProperty(value = "内容-英")
    private String contentEn;
    @ApiModelProperty(value = "简介-英")
    private String introductionEn;
    @ApiModelProperty(value = "图片-英")
    private String imageEn;
    @ApiModelProperty(value = "备注-英")
    private String remarkEn;
    @ApiModelProperty(value = "排序")
    private Integer sort;


    @ApiModelProperty(value = "1中文   2英语")
    private Integer language;


    @ApiModelProperty(value = "名称")
    private String name;//	varchar	255	0	-1	0	0	0							utf8mb4	utf8mb4_general_ci	0	0	0	0	0	0	0
    @ApiModelProperty(value = "邮箱")
    private String email;//	varchar	255	0	-1	0	0	0							utf8mb4	utf8mb4_general_ci	0	0	0	0	0	0	0
    @ApiModelProperty(value = "班级")
    private String classes;//	varchar	255	0	-1	0	0	0							utf8mb4	utf8mb4_general_ci	0	0	0	0	0	0	0
    @ApiModelProperty(value = "名称-英")
    private String nameEn;//	varchar	255	0	-1	0	0	0							utf8mb4	utf8mb4_general_ci	0	0	0	0	0	0	0
    @ApiModelProperty(value = "班级-英")
    private String classesEn;//	varchar	255	0	-1	0	0	0							utf8mb4	utf8mb4_general_ci	0	0	0	0	0	0	0
    @ApiModelProperty(value = "邮箱-英")
    private String emailEn;//	varchar	255	0	-1	0	0	0							utf8mb4	utf8mb4_general_ci	0	0	0	0	0	0	0

}
