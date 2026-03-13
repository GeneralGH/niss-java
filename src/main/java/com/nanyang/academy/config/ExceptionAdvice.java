package com.nanyang.academy.config;


import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 异常控制处理器
 *
 * @author dolyw.com
 * @date 2018/8/30 14:02
 */
@RestControllerAdvice
public class ExceptionAdvice {


    Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);


    /**
     * 捕捉校验异常(BindException)
     *
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResultEntity validException(BindException e) {
        logger.error(e.getMessage());
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, Object> result = this.getValidError(fieldErrors);
        return ResultEntity.getErrorResult(HttpStatus.BAD_REQUEST.value(), fieldErrors.get(0).getDefaultMessage());
    }

    /**
     * 捕捉校验异常(MethodArgumentNotValidException)
     *
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultEntity validException(MethodArgumentNotValidException e) {
        logger.error(e.getMessage());
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, Object> result = this.getValidError(fieldErrors);
        return ResultEntity.getErrorResult(HttpStatus.BAD_REQUEST.value(), fieldErrors.get(0).getDefaultMessage());
    }


    /**
     * 获取校验错误信息
     *
     * @param fieldErrors
     * @return
     */
    private Map<String, Object> getValidError(List<FieldError> fieldErrors) {
        Map<String, Object> result = new HashMap<String, Object>(16);
        List<String> errorList = new ArrayList<String>();
        StringBuffer errorMsg = new StringBuffer("校验异常(ValidException):");
        for (FieldError error : fieldErrors) {
            errorList.add(error.getField() + "-" + error.getDefaultMessage());
            errorMsg.append(error.getField()).append("-").append(error.getDefaultMessage()).append(".");
        }
        result.put("errorList", errorList);
        result.put("errorMsg", errorMsg);
        return result;
    }

    /**
     * 捕捉其他所有自定义异常
     *
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomException.class)
    public ResultEntity<ApiError> handle(CustomException e) {
        // 打印堆栈信息
        ApiError apiError = new ApiError(e.getStatus(), e.getMessage());
        logger.error("处理自定义异常", apiError.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * 统一返回
     *
     * @param apiError
     * @return
     */
    private ResultEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return new ResultEntity(apiError.getStatus(), apiError.getMessage());
    }
}
