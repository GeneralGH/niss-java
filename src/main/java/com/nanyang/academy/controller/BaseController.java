package com.nanyang.academy.controller;

import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.entity.pojo.LoginUser;
import com.nanyang.academy.utils.DateUtils;
import com.nanyang.academy.utils.SecurityUtils;
import com.nanyang.academy.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;

/**
 * @author pt
 * @date 2022-06-08
 */
public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });
    }


    /**
     * 返回成功
     */
    public ResultEntity success() {
        return ResultEntity.getOkResult();
    }

    /**
     * 返回失败消息
     */
    public ResultEntity error() {
        return ResultEntity.getErrorResult();
    }

    /**
     * 返回成功消息
     */
    public ResultEntity success(String message) {
        return ResultEntity.getOkResult(message);
    }

    /**
     * 返回失败消息
     */
    public ResultEntity error(String message) {
        return ResultEntity.getErrorResult(message);
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected ResultEntity toAjax(int rows) {
        return rows > 0 ? ResultEntity.getOkResult() : ResultEntity.getErrorResult();
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected ResultEntity toAjax(boolean result) {
        return result ? success() : error();
    }

    /**
     * 页面跳转
     */
    public String redirect(String url) {
        return StringUtil.format("redirect:{}", url);
    }


    /**
     * 获取登录用户名
     */
    public String getUsername() {
        return getLoginUser().getUsername();
    }

    /**
     * 获取用户缓存信息
     */
    public LoginUser getLoginUser() {
        return SecurityUtils.getLoginUser();
    }

    /**
     * 获取登录用户id
     */
    public Long getUserId() {
        return getLoginUser().getUserId();
    }

    /**
     * 获取登录部门id
     */
    public Long getDeptId() {
        return getLoginUser().getDeptId();
    }

}
