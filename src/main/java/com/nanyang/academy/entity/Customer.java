package com.nanyang.academy.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value="Customer", description="Customer")
public class Customer implements Serializable {


	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value = "")
	private Long id;
	@ApiModelProperty(value = "姓名")
	private String name;
	@ApiModelProperty(value = "国籍")
	private String nationality;
	@ApiModelProperty(value = "所持新加坡居留身份")
	private String residence;
	@ApiModelProperty(value = "学历")
	private String qualification;
	@ApiModelProperty(value = "已在中国境外居住时间")
	private String residenceTime;
	@ApiModelProperty(value = "公司")
	private String company;
	@ApiModelProperty(value = "职位")
	private String post;
	@ApiModelProperty(value = "区号")
	private String areaCode;
	@ApiModelProperty(value = "手机号码")
	private String phone;
	@ApiModelProperty(value = "邮箱地址")
	private String email;
	@ApiModelProperty(value = "您希望以何种方式进行咨询")
	private String consultationMethods;
	@ApiModelProperty(value = "预约咨询日期-月份")
	private String consultationDateMonth;
	@ApiModelProperty(value = "预约咨询日期-日期")
	private String consultationDateDay;
	@ApiModelProperty(value = "预约咨询日期-时间")
	private String consultationDateTime;
	@ApiModelProperty(value = "如何得知暨南大学新加坡MBA硕士学位项目")
	private String obtainingChannels;
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@ApiModelProperty(value = "语言")
	private Integer language;

	@ApiModelProperty(value = "进站来源")
	private String source;

	@ApiModelProperty(value = "咨询项目")
	private String project;

}
