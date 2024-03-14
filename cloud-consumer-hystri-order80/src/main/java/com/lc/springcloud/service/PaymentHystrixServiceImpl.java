package com.lc.springcloud.service;

import cloudapicommons.cloudapicommons.entities.CommonResult;
import org.springframework.stereotype.Component;

@Component
public class PaymentHystrixServiceImpl implements PaymentHystrixService {
    @Override
    public CommonResult<String> paymentInfo_OK(Integer id) {
        return new CommonResult<String>(500, "服务请求错误paymentInfo_OK");
    }

    @Override
    public CommonResult<String> getError(Integer id) {
        return new CommonResult<String>(500, "服务请求修改getError");
    }
}
