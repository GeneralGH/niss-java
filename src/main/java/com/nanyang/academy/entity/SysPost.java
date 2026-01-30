package com.nanyang.academy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value="{职务信息}", description="职务信息")
public class SysPost implements Serializable {

	@ApiModelProperty(value = "岗位ID")
	@TableId
	private Long postId;
	@ApiModelProperty(value = "岗位编码")
	private String postCode;
	@ApiModelProperty(value = "岗位名称")
	private String postName;
	@ApiModelProperty(value = "显示顺序")
	private Integer postSort;

	@ApiModelProperty(value = "状态（0正常 1停用）")
	private Integer isDelete;
	@ApiModelProperty(value = "创建者")
	private String createUser;
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@ApiModelProperty(value = "更新者")
	private String updateUser;
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;
	@ApiModelProperty(value = "备注")
	private String remark;

}
