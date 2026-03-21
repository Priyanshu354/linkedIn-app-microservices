package com.priyanshu.linkedin.post_service.auth;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeinClientInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        Long userId = UserContextHoler.getCurrentUserId();

        if(userId != null){
            requestTemplate.header("X-User-Id", userId.toString());
        }
    }
}
