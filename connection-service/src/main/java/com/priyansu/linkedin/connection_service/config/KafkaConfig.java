package com.priyansu.linkedin.connection_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.internals.Topic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic sendConnectionRequestTopic() {
        return new NewTopic("send-connection-request-topic", 3, (short) 3);
    }

    @Bean
    public NewTopic acceptConnectionRequestTopic() {
        return new NewTopic("accept-connection-request-topic", 3, (short) 3);
    }
}
