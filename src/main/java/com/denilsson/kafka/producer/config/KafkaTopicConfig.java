package com.denilsson.kafka.producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.producer.topic}")
    private String kafkaTopic;

    public NewTopic createNewTopic (){
        return TopicBuilder.name(kafkaTopic).build();
    }
}
