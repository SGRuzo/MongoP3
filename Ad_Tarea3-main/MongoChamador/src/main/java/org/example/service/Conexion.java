package org.example.service;

import org.example.model.Actor;
import org.example.model.Pelicula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

// Servicio que conecta con la API REST de PostgreSQL
@Service
public class Conexion {

    // RestTemplate para hacer peticiones HTTP
    @Autowired
    private RestTemplate restTemplate;

    // URLs de la API REST de PostgreSQL
    private static final String POSTGRES_URL_PELICULAS = "http://localhost:8080/postgres/peliculas";
    private static final String POSTGRES_URL_ACTORES= "http://localhost:8080/postgres/actores";

    // Obtiene TODAS las películas de PostgreSQL
    public List<Pelicula> getAllPeliculas() {
        try {
            String url = POSTGRES_URL_PELICULAS;
            // Hace una petición GET y convierte la respuesta en una Lista de Películas
            ResponseEntity<List<Pelicula>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Pelicula>>() {}
            );
            // Devuelve la lista si existe, si no devuelve una lista vacía
            return response.getBody() != null ? response.getBody() : Collections.emptyList();
        }  catch (Exception e) {
            // Si hay error, imprime el mensaje y devuelve una lista vacía
            System.out.println("Error al obtener las peliculas: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    // Obtiene películas de PostgreSQL por su título
    public List<Pelicula> getPeliculaByTitulo(String titulo) {
        try {
            // Construye la URL con el título de búsqueda
            String url = POSTGRES_URL_PELICULAS + "/titulo/" + titulo;
            // Hace una petición GET y convierte la respuesta en una Lista de Películas
            ResponseEntity<List<Pelicula>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Pelicula>>() {});
            // Devuelve la lista
            return response.getBody();
        } catch (HttpClientErrorException e) {
            // Si hay error (película no encontrada), imprime el mensaje y devuelve null
            System.out.println("Error al obtener la pelicula con titulo " + titulo + ": " + e.getMessage());
            return null;
        }
    }

    // Obtiene UNA película de PostgreSQL por su ID
    public Pelicula getAPeliculaById(Long id) {
        try {
            // Construye la URL con el ID de búsqueda
            String url = POSTGRES_URL_PELICULAS + "/" + id;
            // Hace una petición GET y convierte la respuesta en una Película
            ResponseEntity<Pelicula> response = restTemplate.exchange(url, HttpMethod.GET, null, Pelicula.class);
            // Devuelve la película
            return response.getBody();
        } catch (HttpClientErrorException e) {
            // Si hay error, imprime el mensaje y devuelve null
            System.out.println("Error al obtener la pelicula " + id + ": " + e.getMessage());
            return null;
        }
    }

    // Crea una película en PostgreSQL (mediante POST)
    public Pelicula createPelicula(Pelicula pelicula) {
        try {
            String url = POSTGRES_URL_PELICULAS;
            // Prepara las cabeceras HTTP (especifica que es JSON)
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            // Empaqueta la película en una petición HTTP
            HttpEntity<Pelicula> request = new HttpEntity<>(pelicula, headers);

            // Hace una petición POST con la película
            ResponseEntity<Pelicula> response = restTemplate.exchange(url, HttpMethod.POST, request, Pelicula.class);
            // Devuelve la película creada
            return response.getBody();
        } catch (HttpClientErrorException e) {
            // Si hay error, imprime el mensaje y devuelve null
            System.out.println("Error ao crear Pelicula: " + e.getMessage());
            return null;
        }
    }

}
