package com.nanyang.academy.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

/**
 * @ClassName pt
 * @Description TODO
 * @Author pt
 * @Date 2022/9/22
 * @Version 1.0
 **/
@Slf4j
//@Component
public class RedisListen implements MessageListener{
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();

        //log.info("失效的redis是:"+expiredKey);
        RedisSerializer<?> serializer = redisTemplate.getValueSerializer();
        String channel = String.valueOf(serializer.deserialize(message.getChannel()));
        String body = String.valueOf(serializer.deserialize(message.getBody()));
        //log.info("channel==="+channel+"-----------------"+"body === "+body);
        //key过期监听,在处理业务之前校验下自己业务的key和监听的key以及库号
        if("__keyevent@0__:expired".equals(channel) && body.indexOf("自己业务的key") != -1){
            //log.info("进来了哈");
            //这里写需要处理的业务

        }

    }
}
