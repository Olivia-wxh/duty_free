package com.shangchao.shedule;

import com.shangchao.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class TaskShedule {

    private static final Logger logger = LoggerFactory.getLogger(TaskShedule.class);

    @Autowired
    private TopicService topicService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void checkVerificationCode(){
        //每天凌晨1点同步一次专题表
        logger.debug("每天1点更新数据");
        topicService.resetTopic();
    }
}
