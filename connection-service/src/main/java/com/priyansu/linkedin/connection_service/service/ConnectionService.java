package com.priyansu.linkedin.connection_service.service;

import com.priyansu.linkedin.connection_service.entity.Person;
import com.priyansu.linkedin.connection_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConnectionService {

    private final PersonRepository personRepository;

    public List<Person> getFirstDegreeConnection(Long userId) {
        List<Person> connections = personRepository.getFirstDegreeConnections(userId);
        return connections;
    }
}
