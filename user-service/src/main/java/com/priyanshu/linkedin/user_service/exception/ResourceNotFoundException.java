package com.priyanshu.linkedin.user_service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
    private final Long resourceId;
    private final String resourceName;
}
