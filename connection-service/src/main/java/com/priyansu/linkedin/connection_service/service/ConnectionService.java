package com.priyansu.linkedin.connection_service.service;

import com.priyansu.linkedin.connection_service.entity.Person;
import com.priyansu.linkedin.connection_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionService {

    private final PersonRepository personRepository;

    public List<Person> getFirstDegreeConnection(Long userId) {
        return personRepository.getFirstDegreeConnections(userId);
    }

    public void connectionRequest(Long userId) {
        log.info("Attempt to request sent to userId: {}", userId);
        // store request in PostgreSQL later
    }

    public void acceptConnection(Long senderUserId, Long receiverUserId) {
        log.info("accept connection service hit {}, {}", senderUserId, receiverUserId);

        Person sender = personRepository.findByUserId(senderUserId)
                .orElseGet(() -> {
                    Person p = new Person();
                    p.setUserId(senderUserId);
                    p.setName("User-" + senderUserId);
                    return personRepository.save(p);
                });

        Person receiver = personRepository.findByUserId(receiverUserId)
                .orElseGet(() -> {
                    Person p = new Person();
                    p.setUserId(receiverUserId);
                    p.setName("User-" + receiverUserId);
                    return personRepository.save(p);
                });

        log.info("accept connection service first check pass {}, {}", senderUserId, receiverUserId);

        sender.getConnections().add(receiver);
        receiver.getConnections().add(sender);

        log.info("accept connection service connection saved {}, {}", senderUserId, receiverUserId);

        personRepository.save(sender);
        personRepository.save(receiver);

        log.info("Connection created between {} and {}", senderUserId, receiverUserId);
    }
}