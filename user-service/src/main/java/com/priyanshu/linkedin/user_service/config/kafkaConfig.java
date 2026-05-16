package com.priyanshu.linkedin.user_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class kafkaConfig {

    @Bean
    public NewTopic userCreatedTopic() {
        return new NewTopic("user-created-topic", 3, (short) 3);
    }
}
