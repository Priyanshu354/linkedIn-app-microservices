package com.priyansu.linkedin.connection_service.consumer;

import com.priyansu.linkedin.connection_service.entity.Person;
import com.priyansu.linkedin.connection_service.repository.PersonRepository;
import com.priyansu.linkedin.user_service.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceConsumer {

    private final PersonRepository personRepository;

    @KafkaListener(topics = "user-created-topic")
    void handleUserCreatedTopic(UserCreatedEvent event) {

        log.info("Received UserCreatedEvent for userId={}", event.getUserId());

        if (personRepository.findByUserId(event.getUserId()).isPresent()) {
            log.warn("User already exists in Neo4j: {}", event.getUserId());
            return;
        }

        Person person = new Person();
        person.setUserId(event.getUserId());
        person.setName(event.getName());

        personRepository.save(person);

        log.info("Created Person node for userId={}", event.getUserId());
    }
}
