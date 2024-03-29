package com.lc.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {

    // 配置bean 不然后面没法依赖注入，就像以前ssm整合时配置依赖注入一样，
    // 需要在配置文件配置之后，代码中才可以依赖注入
    // 当前文件就是spring的配置文件
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
