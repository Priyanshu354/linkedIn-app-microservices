package com.priyansu.linkedin.connection_service.repository;

import com.priyansu.linkedin.connection_service.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends Neo4jRepository<Person, Long> {

    Optional<Person> findByUserId(Long userId);

    @Query("""
           MATCH (personA:Person {userId:$userId})-[:CONNECTED_TO]->(personB:Person)
           RETURN personB
           """)
    List<Person> getFirstDegreeConnections(Long userId);

    @Query("""
            MATCH (p1:Person) - [r:REQUESTED_TO] -> (p2:Person)
            WHERE p1.userId = $senderId AND p2.userId = $receiverId
            RETURN count(r) > 0
            """)
    boolean connectionRequestExist(Long senderId, Long receiverId);

    @Query("""
            MATCH (p1:Person) - [r:CONNECTED_TO] -> (p2:Person)
            WHERE p1.userId = $senderId AND p2.userId = $receiverId
            RETURN count(r) > 0
            """)
    boolean alreadyConnected(Long senderId, Long receiverId);

    @Query("""
            MATCH (p1:Person), (p2:Person)
            WHERE p1.userId = $senderId AND p2.userId = $receiverId
            CREATE (p1) - [:REQUESTED_TO] -> (p2)
            """)
    void addConnectionRequest(Long senderId, Long receiverId);

    @Query("""
             MATCH (p1:Person) - [r:REQUESTED_TO] -> (p2:Person)
            WHERE p1.userId = $senderId AND p2.userId = $receiverId
            DELETE r
            CREATE (p1)-[:CONNECTED_TO]->(p2)
            """)
    void acceptedConnectionRequest(Long senderId, Long receiverId);

    @Query("""
             MATCH (p1:Person) - [r:REQUESTED_TO] -> (p2:Person)
            WHERE p1.userId = $senderId AND p2.userId = $receiverId
            DELETE r
            """)
    void rejectConnectionRequest(Long senderId, Long receiverId);

}