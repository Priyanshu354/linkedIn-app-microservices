package com.priyanshu.linkedin.user_service.dto;

import lombok.Data;

@Data
public class LoginOrSignupResponse {
    private String accessToken;
    private Long userId;

}
