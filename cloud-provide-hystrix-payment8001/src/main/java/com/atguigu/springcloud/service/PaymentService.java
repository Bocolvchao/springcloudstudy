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
}
