package com.priyansu.linkedin.connection_service.service;

import com.priyansu.linkedin.connection_service.auth.UserContextHolder;
import com.priyansu.linkedin.connection_service.entity.Person;
import com.priyansu.linkedin.connection_service.event.AcceptConnectionRequestEvent;
import com.priyansu.linkedin.connection_service.event.SendConnectionRequestEvent;
import com.priyansu.linkedin.connection_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionService {

    private final PersonRepository personRepository;
    private final KafkaTemplate<Long, SendConnectionRequestEvent> sendConnectionKafkaTemplate;
    private final KafkaTemplate<Long, AcceptConnectionRequestEvent> acceptConnectionKafkaTemplate;

    public List<Person> getFirstDegreeConnection(Long userId) {
        return personRepository.getFirstDegreeConnections(userId);
    }

    public boolean connectionRequest(Long receiverId) {
        log.info("Attempt to request sent to userId: {}", receiverId);
        Long senderId = UserContextHolder.getCurrentUserId();

        if(senderId.equals(receiverId)) {
            throw new RuntimeException("can't send connection to your own id");
        }

        boolean alreadyRequested = personRepository.connectionRequestExist(senderId, receiverId);

        if(alreadyRequested) {
            throw new RuntimeException("Connection request already exist");
        }

        boolean alreadyConnected = personRepository.alreadyConnected(senderId, receiverId);

        if(alreadyConnected) {
            throw new RuntimeException("Already connected");
        }

        personRepository.addConnectionRequest(senderId, receiverId);
        log.info("Connection request successfully sent");

        SendConnectionRequestEvent sendConnectionRequestEvent = SendConnectionRequestEvent.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .build();

        sendConnectionKafkaTemplate.send("send-connection-request-topic", sendConnectionRequestEvent);
        return true;
    }

    public boolean acceptConnection(Long senderUserId, Long receiverUserId) {
        log.info("accept connection service hit {}, {}", senderUserId, receiverUserId);

        boolean alreadyRequested = personRepository.connectionRequestExist(senderUserId, receiverUserId);

        if(!alreadyRequested) {
            throw new RuntimeException("Connection request doesn't exist");
        }

        personRepository.acceptedConnectionRequest(senderUserId, receiverUserId);

        AcceptConnectionRequestEvent acceptConnectionRequestEvent = AcceptConnectionRequestEvent.builder()
                .senderId(senderUserId)
                .receiverId(receiverUserId)
                .build();

        acceptConnectionKafkaTemplate.send("accept-connection-request-topic", acceptConnectionRequestEvent);

        log.info("Connection created between {} and {}", senderUserId, receiverUserId);
        return true;
    }

    public Boolean rejectRequest(Long senderId) {
        Long receiverId = UserContextHolder.getCurrentUserId();

        boolean alreadyRequested = personRepository.connectionRequestExist(senderId, receiverId);

        if(!alreadyRequested) {
            throw new RuntimeException("Connection request doesn't exist");
        }

        personRepository.rejectConnectionRequest(senderId,receiverId);
        return true;
    }
}