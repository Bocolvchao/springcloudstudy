package com.atguigu.springcloud.config;

import com.google.common.collect.ImmutableMap;
import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;
import java.util.Map;

@Configuration
public class ProxyServletConfiguration {

    @Bean
    public Servlet createProxyServlet() {
        /** 创建新的ProxyServlet */
        return new ProxyServlet();
    }

    @Bean
    public ServletRegistrationBean proxyServletRegistration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(createProxyServlet(), "/proxybaidu/*");
        //设置网址以及参数
        Map<String, String> params = ImmutableMap.of("targetUri", "https://www.baidu.com", "log", "true");
        registrationBean.setInitParameters(params);
        return registrationBean;
    }

    /**
     * fix springboot中使用proxyservlet的 bug.
     * https://github.com/mitre/HTTP-Proxy-Servlet/issues/83
     * https://stackoverflow.com/questions/8522568/why-is-httpservletrequest-inputstream-empty
     *
     * @param filter
     * @return 关闭springboot 自带的 HiddenHttpMethodFilter 防止post提交的form数据流被提前消费
     */
//    @Bean
//    public FilterRegistrationBean registration(HiddenHttpMethodFilter filter) {
//        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
//        registration.setEnabled(false);
//        return registration;
//    }
}
