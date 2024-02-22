package com.atguigu.springcloud.service.impl;

import cloudapicommons.cloudapicommons.entities.Payment;
import com.atguigu.springcloud.dao.PaymentDao;
import com.atguigu.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {


    @Resource
    private PaymentDao paymentDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Payment queryById(Long id) {
        return this.paymentDao.queryById(id);
    }
}
