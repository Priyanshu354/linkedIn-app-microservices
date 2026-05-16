package com.priyanshu.linkedin.notification_service.client;

import com.priyanshu.linkedin.notification_service.dto.PersonDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "connection-service", path = "/connections", url = "${CONNECTIONS_SERVICE_URI}")
public interface ConnectionClient {

    @GetMapping("/core/first-degree")
    @CircuitBreaker(name = "connectionService", fallbackMethod = "getFirstConnectionsFallback")
    List<PersonDto> getFirstConnections(@RequestHeader("X-User-Id") Long userId);

    default List<PersonDto> getFirstConnectionsFallback(Long userId, Exception ex) {
        return List.of();
    }
}
