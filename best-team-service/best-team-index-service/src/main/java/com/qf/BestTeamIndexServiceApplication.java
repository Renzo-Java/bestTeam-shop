package com.qf;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
@MapperScan("com.qf.mapper")
public class BestTeamIndexServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BestTeamIndexServiceApplication.class, args);
    }

}
