package org.example.repository;

import org.example.model.Pelicula;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Interfaz que conecta con MongoDB
@Repository
public interface PeliculaRepository extends MongoRepository<Pelicula, String> {

    // Metodo busca películas por título
    // Spring crea automáticamente la consulta basándose en el nombre del metodo
    List<Pelicula> findByTitulo(String titulo);

}
