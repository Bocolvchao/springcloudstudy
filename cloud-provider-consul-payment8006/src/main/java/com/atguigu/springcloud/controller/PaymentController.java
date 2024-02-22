package com.atguigu.springcloud.controller;

import cloudapicommons.cloudapicommons.entities.CommonResult;
import cloudapicommons.cloudapicommons.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/payment/")
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Resource
    private DiscoveryClient discoveryClient;

    @Value("${server.port}")
    private String port;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("get/{id}")
    public CommonResult<Payment> selectOne(@PathVariable("id") Long id) {
        Payment payment = paymentService.queryById(id);
        log.info("查询数据" + payment + "端口号" + port);
        return new CommonResult<Payment>(200, "查询成功，端口号" + port, payment);
    }

    @GetMapping("getDiscovery")
    public Object getDiscovery() {
        List<String> list = discoveryClient.getServices();
        list.stream().forEach(System.out::println);
        List<ServiceInstance> instanceList = discoveryClient.getInstances("cloud-order-service");
        instanceList.stream().forEach(x -> System.out.println(x.getHost() + "," + x.getInstanceId() + x.getUri()));
        return discoveryClient;
    }

}
