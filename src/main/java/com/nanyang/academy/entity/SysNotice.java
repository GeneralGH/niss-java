package com.nanyang.academy.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value="{SysNotice}", description="SysNotice")
public class SysNotice implements Serializable {


	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value = "主键id")
	private Long id;
	@ApiModelProperty(value = "公告标题")
	private String title;
	@ApiModelProperty(value = "公告内容")
	private String content;
	@ApiModelProperty(value = "关键词")
	private String keyWord;
	@ApiModelProperty(value = "图片")
	private String image;
	@ApiModelProperty(value = "发布人")
	private String createUser;
	@ApiModelProperty(value = "发布时间")
	private Date createTime;

	@ApiModelProperty(value = "修改人")
	private String updateUser;
	@ApiModelProperty(value = "修改时间")
	private Date updateTime;
	@ApiModelProperty(value = "公告状态")
	private Integer status;
	@ApiModelProperty(value = "删除标记")
	private Integer isDelete;

	@ApiModelProperty(value = "公告展示开始时间")
	private Date showTimeStart;
	@ApiModelProperty(value = "公告展示结束时间")
	private Date showTimeEnd;
	@ApiModelProperty(value = "单位性质")
	private String property;

	@ApiModelProperty(value = "单位性质名称")
	private String propertyName;

	@ApiModelProperty(value = "显示时间(秒数)")
	private Integer limitTime;

}
