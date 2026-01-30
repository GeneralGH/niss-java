package com.nanyang.academy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import javax.annotation.Resource;

/**
 * @ClassName pt
 * @Description redis监听配置
 * @Author pt
 * @Date 2022/9/22
 * @Version 1.0
 **/
//@Configuration
public class RedisListenerConfig {
    @Resource
    private RedisConnectionFactory redisConnectionFactory;
    @Resource
    private RedisKeyExpirationListener redisExpiredListener;

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        //监听所有key的过期事件
        redisMessageListenerContainer.addMessageListener(redisExpiredListener, redisExpiredListener.getTopic());
        return redisMessageListenerContainer;
    }
}
