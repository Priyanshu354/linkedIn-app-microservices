package com.priyansu.linkedin.connection_service.controller;

import com.priyansu.linkedin.connection_service.auth.UserContextHolder;
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

    @GetMapping("/first-degree")
    public ResponseEntity<List<Person>> getFirstDegreeConnection() {
        return ResponseEntity.ok(connectionService.getFirstDegreeConnection(UserContextHolder.getCurrentUserId()));
    }

    @PostMapping("/request/{userId}")
    public ResponseEntity<Boolean> connectionRequest(@PathVariable Long userId) {
        return ResponseEntity.ok(connectionService.connectionRequest(userId));
    }

    @PostMapping("/accept/{senderUserId}")
    public ResponseEntity<Boolean> acceptConnection(@PathVariable Long senderUserId) {
        log.info("accept connection controller hit");
        Long currentUserId = UserContextHolder.getCurrentUserId();
        return ResponseEntity.ok(connectionService.acceptConnection(senderUserId, currentUserId));
    }

    @PostMapping("/reject/{userId}")
    public ResponseEntity<Boolean> rejectConnection(@PathVariable Long userId) {
        return ResponseEntity.ok(connectionService.rejectRequest(userId));
    }
 }