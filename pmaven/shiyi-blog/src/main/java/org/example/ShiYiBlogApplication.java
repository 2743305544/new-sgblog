package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;



@ComponentScan("org.example.job")
@EnableScheduling
@ComponentScan("org.example.runner")
@ComponentScan("com.example.aspect")
@ComponentScan("com.example.handler.mybatisplus")
@ComponentScan("com.example.handler.exception")
@ComponentScan("com.example.handler.security")
@ComponentScan("org.example.filter")
@ComponentScan("org.example.config")
@ComponentScan("com.example.utils")
@ComponentScan("com.example.config")
@ComponentScan("org.example.controller")
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@MapperScan("com.example.mapper")
@ComponentScan("com.example.service.impl")
public class ShiYiBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShiYiBlogApplication.class, args);
    }
}
