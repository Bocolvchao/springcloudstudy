package com.atguigu.springcloud.service;

import cloudapicommons.cloudapicommons.entities.Payment;

public interface PaymentService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Payment queryById(Long id);
}
