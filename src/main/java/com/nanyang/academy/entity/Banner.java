package com.nanyang.academy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value="Banner", description="Banner")
public class Banner implements Serializable {


	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value = "")
	private Long id;
	@ApiModelProperty(value = "标题")
	private String title;
	@ApiModelProperty(value = "内容")
	private String content;
	@ApiModelProperty(value = "图片路径")
	private String imageUrl;
	@ApiModelProperty(value = "目标路径")
	private String targetUrl;
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
	@ApiModelProperty(value = "1展示  0不展示")
	private Integer isShow;
	@ApiModelProperty(value = "语言 1中文   2英语")
	private Integer language;

	@ApiModelProperty(value = "排序")
	private Integer sort;


	@ApiModelProperty(value = "标题-英文")
	private String titleEn;
	@ApiModelProperty(value = "内容-英文")
	private String contentEn;
	@ApiModelProperty(value = "图片路径-英文")
	private String imageUrlEn;
	/*@ApiModelProperty(value = "目标路径")
	private String targetUrlEn;*/
	@ApiModelProperty(value = "备注-英文")
	private String remarkEn;

	@ApiModelProperty(value = "标题-移动")
	private String titleMobile;//	varchar	255	0	-1	0	0	0							utf8mb4	utf8mb4_0900_ai_ci	0	0	0	0	0	0	0
	@ApiModelProperty(value = "内容-移动")
	private String contentMobile;//	text	0	0	-1	0	0	0							utf8mb4	utf8mb4_0900_ai_ci	0	0	0	0	0	0	0
	@ApiModelProperty(value = "图片路径-移动")
	private String imageUrlMobile;//	text	0	0	-1	0	0	0							utf8mb4	utf8mb4_0900_ai_ci	0	0	0	0	0	0	0
	@ApiModelProperty(value = "备注-移动")
	private String remarkMobile;//	text	0	0	-1	0	0	0							utf8mb4	utf8mb4_0900_ai_ci	0	0	0	0	0	0	0

	@ApiModelProperty(value = "标题-移动-英文")
	private String titleMobileCn;//	varchar	255	0	-1	0	0	0							utf8mb4	utf8mb4_0900_ai_ci	0	0	0	0	0	0	0
	@ApiModelProperty(value = "内容-移动-英文")
	private String contentMobileCn;//	text	0	0	-1	0	0	0							utf8mb4	utf8mb4_0900_ai_ci	0	0	0	0	0	0	0
	@ApiModelProperty(value = "图片路径-移动-英文")
	private String imageUrlMobileCn;//	text	0	0	-1	0	0	0							utf8mb4	utf8mb4_0900_ai_ci	0	0	0	0	0	0	0
	@ApiModelProperty(value = "备注-移动-英文")
	private String remarkMobileCn;
}
