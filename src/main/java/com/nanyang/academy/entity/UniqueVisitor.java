package com.nanyang.academy.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value="UniqueVisitor", description="UniqueVisitor")
public class UniqueVisitor implements Serializable {

	@ApiModelProperty(value = "")
	private String date;

	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value = "")
	private Long id;
	@ApiModelProperty(value = "")
	private String ip;

}
