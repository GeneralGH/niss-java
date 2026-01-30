package com.nanyang.academy.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value="{SysInfo}", description="SysInfo")
public class SysInfo implements Serializable {


	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value = "")
	private Long id;
	@ApiModelProperty(value = "")
	@TableField("`key`")
	private String key;
	@ApiModelProperty(value = "")
	private String value;
	@ApiModelProperty(value = "修改人")
	private Long updateUser;
	@ApiModelProperty(value = "修改时间")
	private Date updateTime;

}
