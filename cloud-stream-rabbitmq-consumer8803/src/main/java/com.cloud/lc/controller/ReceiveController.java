package com.cloud.lc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@EnableBinding(Sink.class)
public class ReceiveController {


    @Value("${server.port}")
    private String port;

    @StreamListener(Sink.INPUT)
    public void listen(Message<String> message) {
        log.info("消费者" + port + "接受到消息：" + message.getPayload());
    }
}
