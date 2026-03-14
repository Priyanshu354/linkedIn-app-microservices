package com.priyanshu.linkedin.user_service.dto;


import lombok.Data;

@Data
public class SignupRequestDto {
    private String email;
    private String name;
    private String password;
}
