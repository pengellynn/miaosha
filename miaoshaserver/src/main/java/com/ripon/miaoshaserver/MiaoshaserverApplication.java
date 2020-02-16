package com.ripon.miaoshaserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@MapperScan(basePackages = "com.ripon.miaoshaserver.dao")
@SpringBootApplication
public class MiaoshaserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiaoshaserverApplication.class, args);
    }

}
