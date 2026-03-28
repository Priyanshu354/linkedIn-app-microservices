package com.priyanshu.linkedin.notification_service.client;

import com.priyanshu.linkedin.notification_service.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "connection-service", path="/connections", url = "${CONNECTIONS_SERVICE_URI}")
public interface ConnectionClient {

    @GetMapping("/core/first-degree")
    List<PersonDto> getFirstConnections(@RequestHeader("X-User-Id") Long userId);
}
