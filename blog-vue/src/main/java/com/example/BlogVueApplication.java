package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@MapperScan("com.example.mapper")
@EnableScheduling
public class BlogVueApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogVueApplication.class, args);
    }

}
