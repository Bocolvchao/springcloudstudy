package com.lc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class CloudConsumerHystrixDashboard9001 {


    /**
     * http://localhost:9001/hystrix
     * http://192.168.241.143:8001/actuator/hystrix.stream
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(CloudConsumerHystrixDashboard9001.class, args);
        System.out.println("CloudFeginHystriOrder80启动成功");
    }


}
