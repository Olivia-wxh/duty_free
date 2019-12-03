package com.shangchao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@MapperScan(value = {"com.shangchao.dao"})
//@ComponentScan(basePackages = {"com.shangchao.*"})
//@EnableTransactionManagement
public class Start {

    public static void main(String[] args) {
        SpringApplication.run(Start.class, args);
    }
}
