package com.nanyang.academy.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.HttpStatus;

/**
 * Result
 * @author
 */

@ApiModel(description = "消息返回类")
public class ResultEntity<T> {

    /**
     * HTTP状态码
     */
    @ApiModelProperty(value = "状态码")
    private Integer code;

    /**
     * 返回信息
     */
    @ApiModelProperty(value = "描述")
    private String msg;

    /**
     * 返回的数据
     */
    @ApiModelProperty(value = "数据")
    private T data;

    public ResultEntity(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultEntity(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResultEntity getErrorResult(int code, String msg) {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setCode(code);
        resultEntity.setMsg(msg);
        return resultEntity;
    }

    public static ResultEntity getErrorResult(String msg) {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setCode(com.nanyang.academy.common.HttpStatus.ERROR);
        resultEntity.setMsg(msg);
        return resultEntity;
    }

    public static ResultEntity getErrorResult() {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setCode(com.nanyang.academy.common.HttpStatus.ERROR);
        resultEntity.setMsg(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return resultEntity;
    }

    public static <T> ResultEntity<T> getOkResult(final T data) {
        ResultEntity<T> tResultEntity = new ResultEntity<>();
        tResultEntity.setCode(HttpStatus.OK.value());
        tResultEntity.setMsg(HttpStatus.OK.getReasonPhrase());
        tResultEntity.setData(data);
        return tResultEntity;
    }

    public static <T> ResultEntity<T> getOkResult() {
        ResultEntity<T> tResultEntity = new ResultEntity<>();
        tResultEntity.setCode(HttpStatus.OK.value());
        tResultEntity.setMsg(HttpStatus.OK.getReasonPhrase());
        return tResultEntity;
    }

    public static <T> ResultEntity<T> getOkResult(String msg) {
        ResultEntity<T> tResultEntity = new ResultEntity<>();
        tResultEntity.setCode(HttpStatus.OK.value());
        tResultEntity.setMsg(msg);
        return tResultEntity;
    }


    public ResultEntity() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
