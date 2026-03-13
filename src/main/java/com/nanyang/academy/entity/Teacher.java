package com.nanyang.academy.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value="Teacher", description="Teacher")
public class Teacher implements Serializable {


	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value = "id")
	private Long id;
	@ApiModelProperty(value = "姓名")
	private String name;
	@ApiModelProperty(value = "职称")
	private String professional;
	@ApiModelProperty(value = "职务")
	private String post;
	@ApiModelProperty(value = "项目")
	private String project;
	@ApiModelProperty(value = "专业")
	private String major;
	@ApiModelProperty(value = "特长")
	private String speciality;
	@ApiModelProperty(value = "简介")
	private String introduction;
	@ApiModelProperty(value = "图片")
	private String image;
	@ApiModelProperty(value = "备注")
	private String remark;
	@ApiModelProperty(value = "0普通  1精英")
	private String elite;
	@ApiModelProperty(value = "")
	private Long createBy;
	@ApiModelProperty(value = "")
	private Date createTime;
	@ApiModelProperty(value = "")
	private Long updateBy;
	@ApiModelProperty(value = "")
	private Date updateTime;
	@ApiModelProperty(value = "语言")
	private Integer language;

	@ApiModelProperty(value = "pinyin")
	private String pinyin;

	@ApiModelProperty(value = "排序")
	private Integer sort;

	@ApiModelProperty(value = "姓名-英文")
	private String nameEn;
	@ApiModelProperty(value = "职称-英文")
	private String professionalEn;
	@ApiModelProperty(value = "职务-英文")
	private String postEn;
	@ApiModelProperty(value = "项目-英文")
	private String projectEn;
	@ApiModelProperty(value = "专业-英文")
	private String majorEn;
	@ApiModelProperty(value = "特长-英文")
	private String specialityEn;
	@ApiModelProperty(value = "简介-英文")
	private String introductionEn;
	@ApiModelProperty(value = "备注-英文")
	private String remarkEn;
}
