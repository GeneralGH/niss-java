package com.nanyang.academy.entity.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value="{SortChangeParam}", description="SortChangeParam")
public class SortChangeParam {
    private Long first;
    private Long second;

}
