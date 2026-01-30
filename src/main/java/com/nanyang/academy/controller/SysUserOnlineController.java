package com.nanyang.academy.controller;

import com.nanyang.academy.common.Constant;
import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.entity.pojo.LoginUser;
import com.nanyang.academy.entity.pojo.SysUserOnline;
import com.nanyang.academy.service.SysUserOnlineService;
import com.nanyang.academy.utils.RedisCache;
import com.nanyang.academy.utils.StringUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 在线用户监控
 *
 * @author pt
 * @date 2022-06-08
 */
@RestController
@RequestMapping("/api/monitor")
@Api(tags = "在线用户监控")
public class SysUserOnlineController extends BaseController
{
    @Autowired
    private SysUserOnlineService userOnlineService;

    @Autowired
    private RedisCache redisCache;

    @PreAuthorize("@ss.hasPermi('monitor:online:list')")
    @GetMapping("/online/list")
    public ResultEntity list(String ipaddr, String userName)
    {
        Collection<String> keys = redisCache.keys(Constant.LOGIN_TOKEN_KEY + "*");
        List<SysUserOnline> userOnlineList = new ArrayList<SysUserOnline>();
        for (String key : keys)
        {
            LoginUser user = redisCache.getCacheObject(key);
            if (StringUtil.isNotEmpty(ipaddr) && StringUtil.isNotEmpty(userName))
            {
                if (StringUtil.equals(ipaddr, user.getIpaddr()) && StringUtil.equals(userName, user.getUsername()))
                {
                    userOnlineList.add(userOnlineService.selectOnlineByInfo(ipaddr, userName, user));
                }
            }
            else if (StringUtil.isNotEmpty(ipaddr))
            {
                if (StringUtil.equals(ipaddr, user.getIpaddr()))
                {
                    userOnlineList.add(userOnlineService.selectOnlineByIpaddr(ipaddr, user));
                }
            }
            else if (StringUtil.isNotEmpty(userName) && StringUtil.isNotNull(user.getUser()))
            {
                if (StringUtil.equals(userName, user.getUsername()))
                {
                    userOnlineList.add(userOnlineService.selectOnlineByUserName(userName, user));
                }
            }
            else
            {
                userOnlineList.add(userOnlineService.loginUserToUserOnline(user));
            }
        }
        Collections.reverse(userOnlineList);
        userOnlineList.removeAll(Collections.singleton(null));
        return ResultEntity.getOkResult(userOnlineList);
    }


}
