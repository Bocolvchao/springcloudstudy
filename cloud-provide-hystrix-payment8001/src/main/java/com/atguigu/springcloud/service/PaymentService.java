package com.atguigu.springcloud.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentService {


    public String paymentInfo_OK(Integer id) {

        return "线程池" + Thread.currentThread().getName() + "，paymentInfo_OK,编号" + id;
    }


    public String paymentInfo_Error(Integer id) {
        try {
            log.info("paymentInfo_Error请求");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            log.error("paymentInfo_Error" + e);
        }
        return "线程池" + Thread.currentThread().getName() + "，paymentInfo_Error,编号" + id;
    }
}
