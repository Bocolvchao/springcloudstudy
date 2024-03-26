package com.cloud.lc.controller;

import com.cloud.lc.service.IMessageProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class IMessageProviderController {

    @Resource
    private IMessageProvider iMessageProvider;

    @GetMapping("/snedMessage")
    public void sendMesssnedMessageage() {
        iMessageProvider.send();
    }
}
