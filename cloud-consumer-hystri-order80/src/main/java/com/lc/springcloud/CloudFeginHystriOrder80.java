package com.lc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableHystrix
public class CloudFeginHystriOrder80 {
    public static void main(String[] args) {
        SpringApplication.run(CloudFeginHystriOrder80.class, args);
        System.out.println("CloudFeginHystriOrder80启动成功");
    }
}
