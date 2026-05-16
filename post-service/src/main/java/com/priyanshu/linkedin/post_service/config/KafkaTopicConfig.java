package com.priyanshu.linkedin.post_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic postCreateTopic() {
        return TopicBuilder.name("post-created-topic")
                .partitions(3)
                .replicas(3)
                .build();
    }


    @Bean
    public NewTopic postLikeTopic() {
        return TopicBuilder.name("post-like-topic")
                .partitions(3)
                .replicas(3)
                .build();
    }
}
