package com.priyanshu.linkedin.notification_service.consumer;

import com.priyanshu.linkedin.notification_service.client.ConnectionClient;
import com.priyanshu.linkedin.notification_service.dto.PersonDto;
import com.priyanshu.linkedin.notification_service.entity.Notification;
import com.priyanshu.linkedin.notification_service.service.SendNotification;
import com.priyanshu.linkedin.post_service.event.PostCreatedEvent;
import com.priyanshu.linkedin.post_service.event.PostLikeEvent;
import com.priyanshu.linkedin.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceConsumer {

    private final ConnectionClient connectionClient;
    private final SendNotification sendNotification;

    @KafkaListener(topics = "post-created-topic")
    public void handlePostCreated(PostCreatedEvent postCreatedEvent) {
        log.info("Sending notification: handlePostCreated");
        List<PersonDto> connections = connectionClient.getFirstConnections(postCreatedEvent.getCreatorId());

        for(PersonDto connection : connections) {
            sendNotification.send(connection.getUserId(), "Your connection " + postCreatedEvent.getCreatorId()
            + "has created a post, Check it Out!");
        }
    }

    @KafkaListener(topics = "post-like-topic")
    public void handlePostLike(PostLikeEvent postLikeEvent) {
        log.info("Sending notification: handlePostLike");

        String message = String.format("Your post , %d has been liked by %id", postLikeEvent.getPostId(), postLikeEvent.getLikedByUserId());

        sendNotification.send(postLikeEvent.getCreatorId(), message);

    }

}
