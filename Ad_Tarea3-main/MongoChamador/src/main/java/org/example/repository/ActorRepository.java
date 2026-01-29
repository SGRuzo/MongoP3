package org.example.repository;

import org.example.model.Actor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// Interfaz que conecta con MongoDB
@Repository
public interface ActorRepository extends MongoRepository<Actor, String> {
}
