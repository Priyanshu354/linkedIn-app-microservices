package com.priyansu.linkedin.connection_service.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.HashSet;
import java.util.Set;

@Node
@Data
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    private String name;
}