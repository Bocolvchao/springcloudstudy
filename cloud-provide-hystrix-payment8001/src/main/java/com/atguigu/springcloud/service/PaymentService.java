package com.atguigu.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentService {


    public String paymentInfo_OK(Integer id) {

        return "线程池" + Thread.currentThread().getName() + "，paymentInfo_OK,编号" + id;
    }


    @HystrixCommand(fallbackMethod = "paymentInfo_ErrorHandler", commandProperties = {
            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "3000")
    })
    public String paymentInfo_Error(Integer id) {
        try {
            log.info("paymentInfo_Error请求");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            log.error("paymentInfo_Error" + e);
        }
        return "线程池" + Thread.currentThread().getName() + "，paymentInfo_Error,编号" + id;
    }

    public String paymentInfo_ErrorHandler(Integer id) {

        return "您好，业务超时，线程池" + Thread.currentThread().getName() + "，paymentInfo_Error,编号" + id;
    }


    @HystrixCommand(fallbackMethod = "paymentCircuitBreak_ErrorHandler", commandProperties = {
            @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ENABLED, value = "true"), // 是否开启熔断
            @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD, value = "10"), // 请求次数
            @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS, value = "10000"), // 时间窗口期
            @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE, value = "60") // 失败率
    })
    public String paymentCircuitBreak(Integer id) {
        log.info("paymentCircuitBreak请求" + id);
        if (id < 0) {
            throw new RuntimeException("id" + id + "不允许为负数");
        }
        return "线程池" + Thread.currentThread().getName() + "，paymentCircuitBreak,编号" + id;
    }


    public String paymentCircuitBreak_ErrorHandler(Integer id) {

        return "id不允许为负数" + id;
    }

}
