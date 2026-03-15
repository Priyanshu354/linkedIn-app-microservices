package com.priyansu.linkedin.connection_service.controller;

import com.priyansu.linkedin.connection_service.entity.Person;
import com.priyansu.linkedin.connection_service.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class ConnectionController {

    private final ConnectionService connectionService;

    @GetMapping("/{userId}/first-degree")
    public ResponseEntity<List<Person>> getFirstDegreeConnection(@PathVariable Long userId) {
        List<Person> connections = connectionService.getFirstDegreeConnection(userId);
        return ResponseEntity.status(HttpStatus.OK).body(connections);
    }
}
