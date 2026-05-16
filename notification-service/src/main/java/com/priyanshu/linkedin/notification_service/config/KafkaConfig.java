package com.priyanshu.linkedin.notification_service.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.ExponentialBackOff;

@Configuration
@EnableKafka
@Slf4j
public class KafkaConfig {

    /**
     * DLQ Error Handler:
     * - Retries up to 3 times with exponential backoff (1s -> 2s -> 4s)
     * - On final failure, publishes the message to <topic-name>.DLT (Dead Letter Topic)
     * - Prevents a bad message from blocking partition processing indefinitely
     */
    @Bean
    public DefaultErrorHandler kafkaErrorHandler(KafkaTemplate<Object, Object> kafkaTemplate) {
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
                (record, ex) -> {
                    log.error("Message failed after retries. Sending to DLT. topic={}, offset={}, error={}",
                            record.topic(), record.offset(), ex.getMessage());
                    return new org.apache.kafka.common.TopicPartition(
                            record.topic() + ".DLT", record.partition()
                    );
                });

        ExponentialBackOff backOff = new ExponentialBackOff(1000L, 2.0);
        backOff.setMaxAttempts(3);

        DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, backOff);

        // Don't retry on deserialization errors — message is corrupt, send straight to DLT
        errorHandler.addNotRetryableExceptions(
                org.springframework.kafka.support.serializer.DeserializationException.class
        );

        return errorHandler;
    }
}
