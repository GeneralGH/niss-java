package com.nanyang.academy.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value="CustomerSource", description="CustomerSource")
public class CustomerSource implements Serializable {


	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value = "")
	private Long id;
	@ApiModelProperty(value = "网站名称")
	private String name;

	@ApiModelProperty(value = "网站key")
	private String sourceKey;
	@ApiModelProperty(value = "链接")
	private String url;
	@ApiModelProperty(value = "创建人")
	private Long createUser;
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@ApiModelProperty(value = "访问次数")
	private Integer visitNum;

}
