package com.nanyang.academy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 招生简章文件表
 * </p>
 *
 * @author yiyu
 * @since 2026-03-17
 */
@Getter
@Setter
  @TableName("enrollment_brochure")
@ApiModel(value = "EnrollmentBrochure对象", description = "招生简章文件表")
public class EnrollmentBrochure implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("主键ID")
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty("标题")
      private String title;

      @ApiModelProperty("标题英文")
      private String titleEn;

      @ApiModelProperty("文件地址")
      private String filePath;

      @ApiModelProperty("文件（英文）地址")
      private String filePathEn;

      @ApiModelProperty("备注")
      private String remark;

      @ApiModelProperty("创建时间")
      private LocalDateTime createTime;

      @ApiModelProperty("更新时间")
      private LocalDateTime updateTime;
}
