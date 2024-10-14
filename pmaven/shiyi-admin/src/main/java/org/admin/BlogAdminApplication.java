package org.admin;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;



@ComponentScan("org.admin.filter")
@ComponentScan("com.example.aspect")
@ComponentScan("com.example.handler.mybatisplus")
@ComponentScan("com.example.handler.exception")
@ComponentScan("com.example.handler.security")
@ComponentScan("com.example.utils")
@ComponentScan("com.example.config")
@ComponentScan("com.example.service.impl")
@ComponentScan("org.admin.controller")
@ComponentScan("org.admin.config")
@SpringBootApplication
@MapperScan("com.example.mapper")
public class BlogAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogAdminApplication.class, args);
    }
}
