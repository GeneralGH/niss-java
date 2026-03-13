package com.nanyang.academy.config;

import com.nanyang.academy.common.Constant;
import com.nanyang.academy.entity.SysUser;
import com.nanyang.academy.entity.enums.UserType;
import com.nanyang.academy.service.SysUserService;
import com.nanyang.academy.utils.RedisCache;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName pt
 * @Description TODO
 * @Author pt
 * @Date 2022/9/22
 * @Version 1.0
 **/
@Data
//@Component
public class RedisKeyExpirationListener implements MessageListener {
    //监听的主题(只监听redis数据库1，如果要监听redis所有的库，把1替换为*)
    public final PatternTopic topic = new PatternTopic("__keyevent@*__:expired");

    @Autowired
    private SysUserService userService;

    @Autowired
    private RedisCache redisCache;

    /**
     * Redis失效事件 key
     *
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiraKey = message.toString();
        /*RedisSerializer<?> serializer = redisTemplate.getValueSerializer();
        String channel = String.valueOf(serializer.deserialize(message.getChannel()));
        String expiraKey = String.valueOf(serializer.deserialize(message.getBody()));*/

        //key过期监听,在处理业务之前校验下自己业务的key和监听的key以及库号
        if(expiraKey.indexOf(Constant.LOGIN_TOKEN_KEY) != -1){
            //这里写需要处理的业务
            String userName  = expiraKey.substring(expiraKey.indexOf("@_UN")+4);
            System.out.println(userName);
        }
    }
}
