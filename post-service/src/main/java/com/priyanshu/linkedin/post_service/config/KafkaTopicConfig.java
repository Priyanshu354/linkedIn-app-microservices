package com.priyanshu.linkedin.post_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class KafkaTopicConfig {

    @Bean
    public NewTopic postCreateTopic() {
        return TopicBuilder.name("post-created-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }


    @Bean
    public NewTopic postLikeTopic() {
        return TopicBuilder.name("post-like-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
