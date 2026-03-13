package com.nanyang.academy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value="{SysDictType}", description="SysDictType")
public class SysDictType implements Serializable {

	@ApiModelProperty(value = "字典主键")
	@TableId
	private Long dictId;
	@ApiModelProperty(value = "字典名称")
	private String dictName;
	@ApiModelProperty(value = "字典类型")
	private String dictType;
	@ApiModelProperty(value = "状态（0正常 1停用）")
	private String status;
	@ApiModelProperty(value = "创建者")
	private String createBy;
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@ApiModelProperty(value = "更新者")
	private String updateBy;
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;
	@ApiModelProperty(value = "备注")
	private String remark;

}
