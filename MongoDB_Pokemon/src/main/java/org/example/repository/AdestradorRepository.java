package org.example.repository;

import org.example.model.Adestrador;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdestradorRepository extends MongoRepository<Adestrador, String> {

}
