package com.lc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer // 设置为服务注册中心 EurekaServer
public class CloudEurekaServer7001Application {
    public static void main(String[] args) {
        SpringApplication.run(CloudEurekaServer7001Application.class, args);
    }
}