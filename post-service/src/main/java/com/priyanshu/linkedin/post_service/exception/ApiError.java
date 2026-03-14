package com.priyanshu.linkedin.post_service.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
public class ApiError {
    HttpStatus status;
    String message;
    Instant timestamp;

    ApiError(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
        timestamp = Instant.now();
    }
}
