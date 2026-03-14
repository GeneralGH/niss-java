package com.nanyang.academy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value="News", description="News")
public class News implements Serializable {

	@ApiModelProperty(value = "附件")
	private String annex;
	@ApiModelProperty(value = "内容")
	private String content;
	@ApiModelProperty(value = "")
	private Long createBy;
	@ApiModelProperty(value = "")
	private Date createTime;

	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value = "")
	private Long id;
	@ApiModelProperty(value = "备注")
	private String remark;
	@ApiModelProperty(value = "标题")
	private String title;
	@ApiModelProperty(value = "作者")
	private String author;
	@ApiModelProperty(value = "")
	private Long updateBy;
	@ApiModelProperty(value = "")
	private Date updateTime;

	@ApiModelProperty(value = "1学院信息  2校友活动  3加入他们 成为他们")
	private Integer type;
	@ApiModelProperty(value = "语言")
	private Integer language;

	@ApiModelProperty(value = "附件-英文")
	private String annexEn;
	@ApiModelProperty(value = "内容-英文")
	private String contentEn;
	@ApiModelProperty(value = "备注-英文")
	private String remarkEn;
	@ApiModelProperty(value = "标题-英文")
	private String titleEn;
	@ApiModelProperty(value = "作者-英文")
	private String authorEn;

	@ApiModelProperty(value = "排序")
	private Integer sort;

	@ApiModelProperty(value = "推荐")
	private Integer highlight;
}
