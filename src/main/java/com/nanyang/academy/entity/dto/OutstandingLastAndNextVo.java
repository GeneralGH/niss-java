package com.nanyang.academy.entity.dto;

import com.nanyang.academy.entity.News;
import com.nanyang.academy.entity.Outstanding;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value="{OutstandingLastAndNextVo}", description="OutstandingLastAndNextVo")
public class OutstandingLastAndNextVo {

    private Outstanding last;

    private Outstanding next;
}
