package com.lc.springcloud.controller;


import cloudapicommons.cloudapicommons.entities.CommonResult;
import cloudapicommons.cloudapicommons.entities.Payment;
import com.lc.springcloud.lb.LoadBalance;
import com.lc.springcloud.lb.MyLoadBalanceService;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("consumer/")
public class OrderController {
    //    public static final String PAYMENT_URL = "http://localhost:8001";
//    public static final String PAYMENT_URL = "http://CLOUD-PROVIDER-HYSTRIX-PAYMENT";
    public static final String PAYMENT_URL = "http://cloud-provider-lc-payment";


    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalance loadBalance;


    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }


    @GetMapping("getDiscovery")
    public Object getDiscovery() {
        List<ServiceInstance> instanceList = discoveryClient.getInstances("CLOUD-PROVIDER-LC-PAYMENT");
        if (instanceList == null || instanceList.size() <= 0) {
            return null;
        }
        ServiceInstance instances = loadBalance.instances(instanceList);
        return restTemplate.getForObject(instances.getUri() + "/payment/getDiscovery", Object.class);
    }


}
