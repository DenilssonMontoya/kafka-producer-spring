package com.denilsson.kafka.producer.service;

import java.util.concurrent.CompletableFuture;

import com.denilsson.kafka.producer.dto.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageService {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    @Value("${kafka.producer.topic}")
    private String kafkaTopicToSendMessage;
    public CompletableFuture<String> sendMessageEvent(Message message){
        return kafkaTemplate.send(kafkaTopicToSendMessage, message.getFrom(), message)
                .thenApply( event -> "Message sent to: " + event.getProducerRecord().value().getTo());
    }

}
