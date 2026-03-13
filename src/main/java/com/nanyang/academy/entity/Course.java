package com.nanyang.academy.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value="Course", description="Course")
public class Course implements Serializable {


	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value = "")
	private Long id;
	@ApiModelProperty(value = "1必修  2选修")
	private Integer type;
	@ApiModelProperty(value = "标题")
	private String title;
	@ApiModelProperty(value = "内容")
	private String content;
	@ApiModelProperty(value = "状态")
	private Integer status;
	@ApiModelProperty(value = "")
	private Long createBy;
	@ApiModelProperty(value = "")
	private Date createTime;
	@ApiModelProperty(value = "")
	private Long updateBy;
	@ApiModelProperty(value = "")
	private Date updateTime;
	@ApiModelProperty(value = "语言 1中文   2英语")
	private Integer language;

	@ApiModelProperty(value = "")
	private Integer sort;

	@ApiModelProperty(value = "标题-英文")
	private String titleEn;
	@ApiModelProperty(value = "内容-英文")
	private String contentEn;

}
