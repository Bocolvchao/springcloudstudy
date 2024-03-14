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


    /**
     * http://localhost:8001/get/31
     * @param id
     * @return
     */
    @GetMapping("get/{id}")
    public CommonResult<String> paymentInfo_OK(@PathVariable("id") Integer id) {
        String message = paymentService.paymentInfo_OK(id);
        return new CommonResult<String>(200, "查询成功，端口号" + port, message);
    }


    /**
     * http://localhost:8001/getError/31
     * @param id
     * @return
     */
    @GetMapping("getError/{id}")
    public CommonResult<String> getError(@PathVariable("id") Integer id) {
        String message = paymentService.paymentInfo_Error(id);
        return new CommonResult<String>(200, "查询成功，端口号" + port, message);
    }


    /***
     *  http://localhost:8001/paymentCircuitBreak/31
     * 测试
     * @param id
     * @return
     */
    @GetMapping("paymentCircuitBreak/{id}")
    public CommonResult<String> paymentCircuitBreak(@PathVariable("id") Integer id) {
        String message = paymentService.paymentCircuitBreak(id);
        return new CommonResult<String>(200, "查询成功paymentCircuitBreak，端口号" + port, message);
    }

}
