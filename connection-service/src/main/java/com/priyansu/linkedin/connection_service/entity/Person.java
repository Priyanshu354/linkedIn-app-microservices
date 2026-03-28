package com.priyansu.linkedin.connection_service.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

@Node
@Data
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    private String name;
}

