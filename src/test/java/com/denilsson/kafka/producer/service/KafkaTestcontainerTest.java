package com.denilsson.kafka.producer.service;

import com.denilsson.kafka.producer.config.KafkaProducerConfig;
import com.denilsson.kafka.producer.dto.Message;
import com.denilsson.kafka.producer.service.MessageService;
import org.apache.kafka.clients.NetworkClient;
import org.apache.kafka.clients.producer.internals.TransactionManager;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.availability.ApplicationAvailabilityBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

//@SpringBootTest


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@ActiveProfiles("test")
public class KafkaTestcontainerTest {

    @Autowired
    KafkaTemplate kafkaTemplate;
    @Autowired
    MessageService messageService;
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.3.2"));


    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
        registry.add("kafka.producer.topic ", () -> "kfk-message-topic");
    }


    static {
        kafkaContainer.start();
    }

    @Test
    public void shouldSendMessageToKafka(){

        Message message = Message.builder()
                .from("dmontoyane@gmail.com")
                .to("test@gmail.com")
                .body("Message sent to kafka container")
                .build();
        messageService.sendMessageEvent(message);
    }

    @AfterAll
    static void stopAll(){
        kafkaContainer.stop();
    }

}
