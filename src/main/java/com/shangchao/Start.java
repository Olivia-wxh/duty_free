package com.shangchao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(value = {"com.shangchao.dao"})
@EnableCaching
public class Start {

  public static void main(String[] args) {
    SpringApplication.run(Start.class, args);
  }
}
