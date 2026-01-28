package org.example.repository;

import org.example.model.Pelicula;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeliculaRepository extends MongoRepository<Pelicula, Integer> {

    List<Pelicula> findByTitulo(String titulo);

}
