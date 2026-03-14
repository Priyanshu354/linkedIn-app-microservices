package com.priyanshu.linkedin.user_service.dto;

import lombok.Data;

@Data
public class UserProfileResponse {
    private String name;
    private String email;
    private Long userId;
}
