package com.nanyang.academy.entity.dto;
import com.nanyang.academy.entity.ActivityNotice;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "{ActivityNoticeVo}")
public class ActivityNoticeVo  extends ActivityNotice implements Serializable {
}
