package com.atguigu.springcloud.dao;

import cloudapicommons.cloudapicommons.entities.Payment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentDao {
    public Payment queryById(Long id);
}
