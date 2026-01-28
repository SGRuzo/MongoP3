package org.example.service;

import org.example.model.Pelicula;
import org.example.model.Actor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class ConexionService {

    @Autowired
    private RestTemplate restTemplate;


    private static final String POSTGRES_BASE_URL_ACTORES = "http://localhost:8081/postgres/actores";
    private static final String POSTGRES_BASE_URL_PELICULAS = "http://localhost:8081/postgres/peliculas";


    public List<Pelicula> getAllPeliculas() {
        try {
            String url = POSTGRES_BASE_URL_PELICULAS;
            ResponseEntity<List<Pelicula>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Pelicula>>() {}
            );
            return response.getBody() != null ? response.getBody() : Collections.emptyList();
        } catch (HttpClientErrorException e) {
            System.out.println("Error ao obter peliculas: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public Pelicula getPeliculaById(Long id) {
        try {
            String url = POSTGRES_BASE_URL_PELICULAS + "/" + id;
            ResponseEntity<Pelicula> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, Pelicula.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.out.println("Error ao obter pelicula " + id + ": " + e.getMessage());
            return null;
        }
    }

    public Pelicula createPelicula(Pelicula pelicula) {
        try {
            String url = POSTGRES_BASE_URL_PELICULAS;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Pelicula> request = new HttpEntity<>(pelicula, headers);
            ResponseEntity<Pelicula> response = restTemplate.exchange(
                    url, HttpMethod.POST, request, Pelicula.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.out.println("Error ao crear pelicula: " + e.getMessage());
            return null;
        }
    }

    public Pelicula updatePelicula(Long id, Pelicula datos) {
        try {
            String url = POSTGRES_BASE_URL_PELICULAS + "/" + id;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Pelicula> request = new HttpEntity<>(datos, headers);
            ResponseEntity<Pelicula> response = restTemplate.exchange(
                    url, HttpMethod.PUT, request, Pelicula.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.out.println("Error ao actualizar pelicula " + id + ": " + e.getMessage());
            return null;
        }
    }

    public boolean deletePelicula(Long id) {
        try {
            String url = POSTGRES_BASE_URL_PELICULAS + "/" + id;
            restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
            return true;
        } catch (HttpClientErrorException e) {
            System.out.println("Error ao borrar pelicula " + id + ": " + e.getMessage());
            return false;
        }
    }
}
