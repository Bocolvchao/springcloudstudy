package com.lc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CloudFeginOrder80 {

    public static void main(String[] args) {
        SpringApplication.run(CloudFeginOrder80.class, args);
        System.out.println("CloudFeginOrder80启动成功");
    }
}
