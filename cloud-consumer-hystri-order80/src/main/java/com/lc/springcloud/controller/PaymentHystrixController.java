package com.lc.springcloud.controller;

import cloudapicommons.cloudapicommons.entities.CommonResult;
import com.lc.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("consumer/")
@Slf4j
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class PaymentHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    /**
     * http://localhost/consumer/getError/33
     *
     * @param id
     * @return
     */
    @GetMapping("getError/{id}")
    @HystrixCommand(fallbackMethod = "getErrorHandler", commandProperties = {
            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "1500")
    })
    public CommonResult<String> getError(@PathVariable("id") Integer id) {
        return paymentHystrixService.getError(id);
    }

    public CommonResult<String> getErrorHandler(@PathVariable("id") Integer id) {

        return new CommonResult<String>(200, "单独默认方法，不好意思，服务超时", "超时了，爱用不用");
    }


    /**
     * http://localhost/consumer/paymentInfo_OK/get/33
     *
     * @param id
     * @return
     */
    @GetMapping("paymentInfo_OK/get/{id}")
    public CommonResult<String> paymentInfo_OK(@PathVariable("id") Integer id) {

        return paymentHystrixService.paymentInfo_OK(id);
    }


    /**
     * http://localhost/consumer/getOtherError/33
     *
     * @param id
     * @return
     */
    @GetMapping("getOtherError/{id}")
    @HystrixCommand()
    public CommonResult<String> getOtherError(@PathVariable("id") Integer id) {
        return paymentHystrixService.getError(id);
    }


    public CommonResult<String> payment_Global_FallbackMethod() {
        return new CommonResult<String>(200, "全局默认方法，不好意思，服务超时", "超时了，爱用不用");
    }


}
