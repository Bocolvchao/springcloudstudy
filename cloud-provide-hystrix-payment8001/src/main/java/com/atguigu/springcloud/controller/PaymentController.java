package com.atguigu.springcloud.controller;

import cloudapicommons.cloudapicommons.entities.CommonResult;
import com.atguigu.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String port;


    @GetMapping("get/{id}")
    public CommonResult<String> paymentInfo_OK(@PathVariable("id") Integer id) {
        String message = paymentService.paymentInfo_OK(id);
        return new CommonResult<String>(200, "查询成功，端口号" + port, message);
    }


    @GetMapping("getError/{id}")
    public CommonResult<String> getError(@PathVariable("id") Integer id) {
        String message = paymentService.paymentInfo_Error(id);
        return new CommonResult<String>(200, "查询成功，端口号" + port, message);
    }

}