package com.nanyang.academy.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value="Outstanding", description="Outstanding")
public class Outstanding implements Serializable {

	@ApiModelProperty(value = "公司")
	private String company;
	@ApiModelProperty(value = "")
	private Long createBy;
	@ApiModelProperty(value = "")
	private Date createTime;
	@ApiModelProperty(value = "年级班级")
	private String grade;

	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value = "")
	private Long id;
	@ApiModelProperty(value = "图片")
	private String image;
	@ApiModelProperty(value = "1中文   2英语")
	private Integer language;
	@ApiModelProperty(value = "姓名")
	private String name;
	@ApiModelProperty(value = "备注")
	private String remark;
	@ApiModelProperty(value = "")
	private Long updateBy;
	@ApiModelProperty(value = "")
	private Date updateTime;
	@ApiModelProperty(value = "职务")
	private String workPost;

	@ApiModelProperty(value = "排序")
	private Integer sort;

	@ApiModelProperty(value = "简介")
	private String introduction;

	@ApiModelProperty(value = "简介--英文")
	private String introductionEn;
	@ApiModelProperty(value = "公司--英文")
	private String companyEn;
	@ApiModelProperty(value = "职务-英文")
	private String workPostEn;
	@ApiModelProperty(value = "年级班级-英文")
	private String gradeEn;
	@ApiModelProperty(value = "图片-英文")
	private String imageEn;
	@ApiModelProperty(value = "姓名-英文")
	private String nameEn;
	@ApiModelProperty(value = "备注-英文")
	private String remarkEn;
}
