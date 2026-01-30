package com.nanyang.academy.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Slf4j
public class JobSchedule{

    /**
     * 会话完结任务
     * @author pt
     * @date 9:16 2022/9/4  
     **/
    @Scheduled(cron = "0 1/1 * * * *")
    @Transactional
    public void talkingClose(){

    }
}
