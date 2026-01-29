package org.example.repository;

import org.example.model.Presidente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresidenteRepository extends MongoRepository<Presidente, String> {

}
