package com.lc.springcloud.service;


import cloudapicommons.cloudapicommons.entities.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "cloud-provide-hystrix-payment8001", fallback = PaymentHystrixServiceImpl.class)
public interface PaymentHystrixService {

    @GetMapping("get/{id}")
    public CommonResult<String> paymentInfo_OK(@PathVariable("id") Integer id);

    @GetMapping("getError/{id}")
    public CommonResult<String> getError(@PathVariable("id") Integer id);
}
