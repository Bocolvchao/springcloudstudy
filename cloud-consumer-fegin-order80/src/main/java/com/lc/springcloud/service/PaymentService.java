package com.lc.springcloud.service;


import cloudapicommons.cloudapicommons.entities.CommonResult;
import cloudapicommons.cloudapicommons.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "cloud-provider-lc-payment")
public interface PaymentService {

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> selectOne(@PathVariable("id") Long id);


    @GetMapping("/payment/feignTimeout")
    public CommonResult<Payment> feignTimeout();
}
