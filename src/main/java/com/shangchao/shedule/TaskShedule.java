package com.shangchao.shedule;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class TaskShedule {

    @Scheduled(cron = "0 0 1 * * ?")
    public void checkVerificationCode(){
        //每天凌晨1点同步一次专题表

    }
}
