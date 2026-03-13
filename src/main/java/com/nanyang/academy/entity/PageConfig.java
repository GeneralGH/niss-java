package com.nanyang.academy.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value="PageConfig", description="PageConfig")
public class PageConfig implements Serializable {


	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value = "")
	private Long id;
	@ApiModelProperty(value = "标题")
	private String title;
	@ApiModelProperty(value = "文本")
	private String content;
	@ApiModelProperty(value = "路径")
	private String url;
	@ApiModelProperty(value = "位置")
	private String position;
	@ApiModelProperty(value = "")
	private Long createBy;
	@ApiModelProperty(value = "")
	private Date createTime;
	@ApiModelProperty(value = "")
	private Long updateBy;
	@ApiModelProperty(value = "")
	private Date updateTime;
	@ApiModelProperty(value = "1文本  2图片")
	private Integer type;
	@ApiModelProperty(value = "备注")
	private String remark;
	@ApiModelProperty(value = "顺序")
	private Integer sequence;
	@ApiModelProperty(value = "单位")
	private String unit;
	@ApiModelProperty(value = "语言")
	private Integer language;

	@ApiModelProperty(value = "标题-英文")
	private String titleEn;
	@ApiModelProperty(value = "文本-英文")
	private String contentEn;
	@ApiModelProperty(value = "路径-英文")
	private String urlEn;
	@ApiModelProperty(value = "备注-英文")
	private String remarkEn;
	@ApiModelProperty(value = "单位-英文")
	private String unitEn;

	@ApiModelProperty(value = "目标url")
	private String targetUrl;
	@ApiModelProperty(value = "目标url-英文")
	private String targetUrlEn;

	@ApiModelProperty(value = "标题-移动-英文")
	private String titleMobileEn;
	@ApiModelProperty(value = "文本-移动-英文")
	private String contentMobileEn;//	varchar	255	0	-1	0	0	0							utf8mb4	utf8mb4_0900_ai_ci	0	0	0	0	0	0	0
	@ApiModelProperty(value = "路径-移动-英文")
	private String urlMobileEn;//	varchar	255	0	-1	0	0	0							utf8mb4	utf8mb4_0900_ai_ci	0	0	0	0	0	0	0
	@ApiModelProperty(value = "备注-移动-英文")
	private String remarkMobileEn;//	varchar	255	0	-1	0	0	0							utf8mb4	utf8mb4_0900_ai_ci	0	0	0	0	0	0	0
	@ApiModelProperty(value = "单位-移动-英文")
	private String unitMobileEn;//	varchar	255	0	-1	0	0	0							utf8mb4	utf8mb4_0900_ai_ci	0	0	0	0	0	0	0
	@ApiModelProperty(value = "目标url-移动-英文")
	private String targetMobileUrlEn;//	varchar	255	0	-1	0	0	0							utf8mb4	utf8mb4_0900_ai_ci	0	0	0	0	0	0	0
	@ApiModelProperty(value = "标题-移动")
	private String titleMobile;//	varchar	255	0	-1	0	0	0							utf8mb4	utf8mb4_0900_ai_ci	0	0	0	0	0	0	0
	@ApiModelProperty(value = "文本-移动")
	private String contentMobile;//	varchar	255	0	-1	0	0	0							utf8mb4	utf8mb4_0900_ai_ci	0	0	0	0	0	0	0
	@ApiModelProperty(value = "路径-移动")
	private String urlMobile;//	varchar	255	0	-1	0	0	0							utf8mb4	utf8mb4_0900_ai_ci	0	0	0	0	0	0	0
	@ApiModelProperty(value = "备注-移动")
	private String remarkMobile;//	varchar	255	0	-1	0	0	0							utf8mb4	utf8mb4_0900_ai_ci	0	0	0	0	0	0	0
	@ApiModelProperty(value = "单位-移动")
	private String unitMobile;//	varchar	255	0	-1	0	0	0							utf8mb4	utf8mb4_0900_ai_ci	0	0	0	0	0	0	0
	@ApiModelProperty(value = "目标url-移动")
	private String targetMobileUrl;//	varchar	255	0	-1	0	0	0							utf8mb4	utf8mb4_0900_ai_ci	0	0	0	0	0	0	0


}
