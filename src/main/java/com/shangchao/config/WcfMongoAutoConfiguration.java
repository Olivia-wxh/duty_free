package com.shangchao.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;


/**
 * @author wangcanfeng
 * @description 配置mongoDB的属性
 * @Date Created in 11:13-2019/3/19
 */
@Configuration
public class WcfMongoAutoConfiguration {


    /**
     * 功能描述: 根据自己创建的工厂初始化一个template
     *
     * @param
     * @return:org.springframework.data.mongodb.core.MongoTemplate
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/21 14:23
     */
    @Bean
    @Primary
    public MongoTemplate template() {
        return new MongoTemplate(factory());
    }

    /**
     * 功能描述: 创建数据库名称对应的工厂，数据库名称可以通过配置文件导入
     *
     * @param
     * @return:org.springframework.data.mongodb.MongoDbFactory
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/21 14:22
     */
    @Bean("mongoDbFactory")
    public MongoDbFactory factory() {
        return new SimpleMongoDbFactory(client(), "test");
    }

    /**
     * 功能描述: 配置client，client中传入的ip和端口可以通过配置文件读入
     *
     * @param
     * @return:com.mongodb.MongoClient
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/21 14:23
     */
    @Bean("mongoClient")
    public MongoClient client() {
        return new MongoClient("10.19.131.65", 7000);
    }

}