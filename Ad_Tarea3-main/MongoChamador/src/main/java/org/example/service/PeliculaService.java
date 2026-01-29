package org.example.service;

import com.google.gson.Gson;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.model.Pelicula;
import org.example.repository.ActorRepository;
import org.example.repository.PeliculaRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

// Servicio para gestionar películas en MongoDB
@Service
public class PeliculaService {

    // Repositorio de MongoDB para películas
    private final PeliculaRepository peliculaRepository;

    // Constructor: recibe el repositorio
    public PeliculaService(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    // Guarda una película en MongoDB
    public void crearPelicula(Pelicula pelicula){
        peliculaRepository.save(pelicula); // Guarda un solo documento
    }

    // Obtiene TODAS las películas de MongoDB
    public List<Pelicula> findAll(){
        return peliculaRepository.findAll(); // Recupera todos los documentos de la colección
    }

    // Lee películas desde un archivo JSON y las guarda en MongoDB
    public List<Pelicula> importarDesdeJSON(String ruta) throws Exception {
        try(Reader reader = new InputStreamReader(new ClassPathResource(ruta).getInputStream())) {
            // Especifica que vamos a leer una Lista de Películas
            Type listType = new com.google.gson.reflect.TypeToken<List<Pelicula>>() {
            }.getType();
            // Convierte el JSON a objetos Java usando Gson
            List<Pelicula> lista_peliculas = new Gson().fromJson(reader, listType);
            // Guarda todas las películas en MongoDB de una vez
            return peliculaRepository.saveAll(lista_peliculas);
        }
    }

    // Obtiene una película por su ID
    public Pelicula obtenerPorId(String id){
        // findById devuelve un Optional (puede tener valor o no)
        // .orElse(null) devuelve null si no existe
        return peliculaRepository.findById(id).orElse(null);
    }

    // Obtiene la primera película que coincida con el título
    public Pelicula obtenerPorTitulo(String titulo){
        // Obtiene una lista de películas con ese título
        List<Pelicula> lista = peliculaRepository.findByTitulo(titulo);
        // Si la lista está vacía devuelve null, si no devuelve la primera película
        return lista.isEmpty() ? null : lista.get(0);
    }

    // Exporta TODAS las películas de MongoDB a un archivo JSON
    public void exportarAJSON() {
        // Obtiene todas las películas
        List<Pelicula> lista_peliculas = findAll();

        try {
            // ObjectMapper = herramienta para convertir objetos Java a JSON
            ObjectMapper mapper = new ObjectMapper();
            // Enable: formatea el JSON de forma legible (con indentación)
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            // Escribe la lista de películas en el archivo "peliculas.json"
            mapper.writeValue(new File("peliculas.json"), lista_peliculas);
            // Mensaje de éxito
            System.out.println("Peliculas exportadas a peliculas.json");

        } catch (IOException e) {
            // Si hay error al escribir el archivo, imprime el mensaje
            System.out.println("Error al exportar las peliculas a JSON: " + e.getMessage());
        }
    }
}
