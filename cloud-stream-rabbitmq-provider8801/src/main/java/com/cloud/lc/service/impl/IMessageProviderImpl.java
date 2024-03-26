package com.cloud.lc.service.impl;

import com.cloud.lc.service.IMessageProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Slf4j
@Service
@EnableBinding(Source.class) // 定义消息的推送管道
public class IMessageProviderImpl implements IMessageProvider {


    // 消息发送管道
    @Resource
    @Output(Source.OUTPUT)
    private MessageChannel messageChannel;

    @Override
    public String send() {
        String uuid = UUID.randomUUID().toString();
        messageChannel.send(MessageBuilder.withPayload(uuid).build());
        log.info("发送消息{}", uuid);

        return "";
    }
}
