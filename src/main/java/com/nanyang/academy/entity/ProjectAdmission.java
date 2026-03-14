package com.nanyang.academy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("project_admission")
@ApiModel(value = "ProjectAdmission", description = "项目与招生")
public class ProjectAdmission implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "头部图片")
    private String headImage;
    @ApiModelProperty(value = "头部图片-英文")
    private String headImageEn;

    @ApiModelProperty(value = "简介")
    private String intro;
    @ApiModelProperty(value = "简介-英文")
    private String introEn;

    @ApiModelProperty(value = "活动时间")
    private String activityTime;
    @ApiModelProperty(value = "活动时间-英文")
    private String activityTimeEn;

    @ApiModelProperty(value = "图标")
    private String icon;
    @ApiModelProperty(value = "图标-英文")
    private String iconEn;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}