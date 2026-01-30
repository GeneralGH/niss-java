package com.nanyang.academy.entity.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @ClassName pt
 * @Description TODO
 * @Author pt
 * @Date 2022/7/18
 * @Version 1.0
 **/
@Data
public class LoginSmsInput implements Serializable {
    @NotBlank
    private String phone;
    @NotBlank
    private String code;

    @ApiModelProperty(value = "唯一标识")
    private String uuid;
}
