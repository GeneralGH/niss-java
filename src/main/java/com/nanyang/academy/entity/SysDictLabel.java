package com.nanyang.academy.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(value="{SysDictLabel}", description="SysDictLabel")
public class SysDictLabel implements Serializable {


	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value = "主键id")
	private Long id;
	@ApiModelProperty(value = "标签名称")
	private String name;
	@ApiModelProperty(value = "上级标签id")
	private Long pid;
	@ApiModelProperty(value = "上级标签名称")
	private String pname;
	@ApiModelProperty(value = "标签级别")
	private Integer level;
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@ApiModelProperty(value = "创建人")
	private String createUser;
	@ApiModelProperty(value = "标签类型(1知识库标签分类 2单位性质)")
	private Integer type;

	@ApiModelProperty(value = "图标")
	private String icon;
	@ApiModelProperty(value = "子标签")
	@TableField(exist = false)
	private List<SysDictLabel> childList;

	private String updateUser;

	private Date updateTime;

}
