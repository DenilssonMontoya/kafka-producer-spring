package com.denilsson.kafka.producer.controller;

import java.util.concurrent.CompletableFuture;

import com.denilsson.kafka.producer.dto.Message;
import com.denilsson.kafka.producer.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/publish")
    public CompletableFuture<String> publishMessage(@RequestBody Message message) {
        return messageService.sendMessageEvent(message);
    }

}
