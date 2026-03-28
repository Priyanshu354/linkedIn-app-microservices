package com.priyanshu.linkedin.user_service.event;

import lombok.Builder;
import lombok.Data;

@Data
public class UserCreatedEvent {
    String name;
    Long userId;
}
