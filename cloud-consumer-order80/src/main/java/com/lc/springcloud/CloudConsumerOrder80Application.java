package com.lc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
//@RibbonClient(name = "cloud-provider-lc-payment",configuration = MySelfRuler.class)
public class CloudConsumerOrder80Application {
    public static void main(String[] args) {
        SpringApplication.run(CloudConsumerOrder80Application.class, args);
        System.out.println("CloudConsumerOrder80启动成功");
    }
}
