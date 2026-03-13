package com.nanyang.academy.entity.dto;

import com.nanyang.academy.entity.News;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value="{LastAndNextVo}", description="LastAndNextVo")
public class LastAndNextVo {
    private News last;

    private News next;
}
