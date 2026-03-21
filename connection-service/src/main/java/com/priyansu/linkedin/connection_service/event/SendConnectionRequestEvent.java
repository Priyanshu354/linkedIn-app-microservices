package com.priyansu.linkedin.connection_service.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendConnectionRequestEvent {
    private Long senderId;
    private Long receiverId;
}
