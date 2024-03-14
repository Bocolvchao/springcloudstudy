package com.lc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
public class CloudConsumerOrderZK80Application {

    public static void main(String[] args) {
        SpringApplication.run(CloudConsumerOrderZK80Application.class, args);
        System.out.println("CloudConsumerOrderZK80Application启动成功");

    }

}
