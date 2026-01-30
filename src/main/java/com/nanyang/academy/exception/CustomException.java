package com.nanyang.academy.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 自定义异常(CustomException)
 * @author dolyw.com
 * @date 2018/8/30 13:59
 */
@Getter
public class CustomException extends RuntimeException {
    private Integer status = HttpStatus.BAD_REQUEST.value();
    public CustomException(String msg) {
        super(msg);
    }

    public CustomException(HttpStatus status, String msg){
        super(msg);
        this.status = status.value();
    }
}
