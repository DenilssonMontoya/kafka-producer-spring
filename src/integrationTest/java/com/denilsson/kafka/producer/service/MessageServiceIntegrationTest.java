package com.denilsson.kafka.producer.service;

import com.denilsson.kafka.producer.dto.Message;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
public class MessageServiceIntegrationTest {

    static Logger logger = LoggerFactory.getLogger(MessageServiceIntegrationTest.class);

    @Autowired
    MessageService messageService;

    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.3.2"));

    static{
        kafkaContainer.start();
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("kafka.producer.bootstrap-servers", () -> kafkaContainer.getBootstrapServers());
        registry.add("kafka.producer.topic ", () -> "kfk-message-topic");
    }

    @Test
    public void shouldSendMessageToKafka() throws ExecutionException, InterruptedException {
        Message message = Message.builder()
                .from("dmontoyane@gmail.com")
                .to("test@gmail.com")
                .body("Message sent to kafka container")
                .build();
        String kafkaMessage = messageService.sendMessageEvent(message).get();
        logger.info(kafkaMessage);
        assertThat(kafkaMessage).startsWith("Message sent to: ");
    }

    @AfterAll
    static void stopAll(){
        logger.info("Stopping kafka container ");
        kafkaContainer.stop();
        logger.info("kafka container stopped ");
    }

}