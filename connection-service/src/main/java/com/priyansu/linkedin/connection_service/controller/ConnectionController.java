package com.priyansu.linkedin.connection_service.controller;

import com.priyansu.linkedin.connection_service.entity.Person;
import com.priyansu.linkedin.connection_service.service.ConnectionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@Slf4j
public class ConnectionController {

    private final ConnectionService connectionService;

    @GetMapping("/{userId}/first-degree")
    public ResponseEntity<List<Person>> getFirstDegreeConnection(@PathVariable Long userId) {
        return ResponseEntity.ok(connectionService.getFirstDegreeConnection(userId));
    }

    @PostMapping("/request/{userId}")
    public ResponseEntity<Void> connectionRequest(@PathVariable Long userId) {
        connectionService.connectionRequest(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/accept/{senderUserId}")
    public ResponseEntity<Void> acceptConnection(@PathVariable Long senderUserId,
                                                 HttpServletRequest request) {
        log.info("accept connection controller hit");
        Long currentUserId = 1L;
        connectionService.acceptConnection(senderUserId, currentUserId);

        return ResponseEntity.noContent().build();
    }
}