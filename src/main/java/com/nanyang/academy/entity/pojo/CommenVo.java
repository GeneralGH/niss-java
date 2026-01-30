package com.nanyang.academy.entity.pojo;

import com.nanyang.academy.utils.DateUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author xj
 * @title: LawVo
 * @projectName gy
 * @description: TODO
 * @date 2022/7/18 15:15
 */
@Data
public class CommenVo {

    @ApiModelProperty(value = "")
    private Long id;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "封面")
    private String imgUrl;
    @ApiModelProperty(value = "")
    private Date createTime;

    public String getCreateTime() {
        String time = DateUtils.dateTime(createTime);
        return time;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
