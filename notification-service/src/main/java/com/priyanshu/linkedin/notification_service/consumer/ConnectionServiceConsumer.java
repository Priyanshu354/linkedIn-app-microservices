package com.priyanshu.linkedin.notification_service.consumer;

import com.priyanshu.linkedin.connection_service.event.AcceptConnectionRequestEvent;
import com.priyanshu.linkedin.connection_service.event.SendConnectionRequestEvent;
import com.priyanshu.linkedin.notification_service.service.SendNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionServiceConsumer {

    private final SendNotification sendNotification;

    @KafkaListener(topics = "send-connection-request-topic")
    public void handleSendConnectionRequest(SendConnectionRequestEvent sendConnectionRequestEvent) {
        log.info("Sending notification: handleSendConnectionRequest");
        String message = String.format(
                "You have received a connection request from user with id: %d",
                sendConnectionRequestEvent.getSenderId()
        );
        sendNotification.send(sendConnectionRequestEvent.getReceiverId(), message);
    }

    @KafkaListener(topics = "accept-connection-request-topic")
    public void handleAcceptConnectionRequest(AcceptConnectionRequestEvent acceptConnectionRequestEvent) {
        log.info("Sending notification: handleAcceptConnectionRequest");
        String message = String.format(
                "Your connection request accepted by id: %d",
                acceptConnectionRequestEvent.getReceiverId()
        );
        sendNotification.send(acceptConnectionRequestEvent.getSenderId(), message);
    }
}
