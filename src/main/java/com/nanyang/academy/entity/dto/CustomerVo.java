package com.nanyang.academy.entity.dto;

import com.nanyang.academy.entity.Customer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="{CustomerVo}", description="CustomerVo")
public class CustomerVo extends Customer implements Serializable {
}
