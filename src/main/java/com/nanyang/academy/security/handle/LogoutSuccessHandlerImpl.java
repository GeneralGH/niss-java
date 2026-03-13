package com.nanyang.academy.security.handle;

import com.alibaba.fastjson.JSON;
import com.nanyang.academy.common.Constant;
import com.nanyang.academy.common.HttpStatus;
import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.entity.pojo.LoginUser;
import com.nanyang.academy.manager.AsyncFactory;
import com.nanyang.academy.manager.AsyncManager;
import com.nanyang.academy.service.impl.TokenService;
import com.nanyang.academy.utils.ServletUtils;
import com.nanyang.academy.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author pt
 * @date 2022-05-30
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Autowired
    private TokenService tokenService;

    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtil.isNotNull(loginUser))
        {
            String userName = loginUser.getUsername();
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
            // 记录用户退出日志
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constant.LOGOUT, "退出成功"));
        }
        ServletUtils.renderString(response, JSON.toJSONString(new ResultEntity(HttpStatus.SUCCESS, "退出成功")));
    }
}
