package com.lc.springcloud.controller;

import cloudapicommons.cloudapicommons.entities.CommonResult;
import cloudapicommons.cloudapicommons.entities.Payment;
import com.lc.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("consumer/")
@Slf4j
public class OrderController {

    @Resource
    private PaymentService paymentService;

    @GetMapping("get/{id}")
    public CommonResult<Payment> selectOne(@PathVariable("id") Long id) {
        CommonResult<Payment> result = paymentService.selectOne(id);
        return result;
    }

    @GetMapping("/payment/feignTimeout")
    public CommonResult<Payment> feignTimeout() {
        return paymentService.feignTimeout();
    }

}
