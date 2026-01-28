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

/**
 * @Service: Indica que esta clase contiene la lógica de negocio.
 * Spring la detecta automáticamente y permite inyectarla en los controladores.
 */
@Service
public class PeliculaService {

    private final PeliculaRepository peliculaRepository;
    private final ActorRepository actorRepository;

    /**
     * Inyección por constructor: Es la forma recomendada en Spring Boot.
     * Spring inyecta automáticamente las implementaciones de los repositorios.
     */
    public PeliculaService(PeliculaRepository peliculaRepository, ActorRepository actorRepository) {
        this.peliculaRepository = peliculaRepository;
        this.actorRepository = actorRepository;
    }

    public void crearPelicula(Pelicula pelicula){
        peliculaRepository.save(pelicula); // Guarda un solo documento
    }

    public List<Pelicula> findAll(){
        return peliculaRepository.findAll(); // Recupera todos los documentos de la colección
    }

    /**
     * Importar desde JSON: Muy común en exámenes de AD.
     * Usa Gson para convertir el archivo en una lista de objetos Pelicula.
     * saveAll: Inserta todos los objetos en MongoDB en una sola operación eficiente.
     */
    public List<Pelicula> importarDesdeJSON(String ruta) throws Exception {
        try(Reader reader = new InputStreamReader(new ClassPathResource(ruta).getInputStream())) {

            Type listType = new com.google.gson.reflect.TypeToken<List<Pelicula>>() {
            }.getType();

            List<Pelicula> lista_peliculas = new Gson().fromJson(reader, listType);

            return peliculaRepository.saveAll(lista_peliculas);
        }
    }

    public Pelicula obtenerPorId(Integer id){
        // findById devuelve un Optional. .orElse(null) es una forma de manejar si no existe.
        return peliculaRepository.findById(id).orElse(null);
    }

    public Pelicula obtenerPorTitulo(String titulo){
        List<Pelicula> lista = peliculaRepository.findByTitulo(titulo);
        return lista.isEmpty() ? null : lista.get(0);
    }

    /**
     * Exportar a JSON: Usa Jackson (ObjectMapper) para convertir objetos Java a formato JSON.
     * mapper.enable(SerializationFeature.INDENT_OUTPUT): Para que el JSON sea legible (con espacios).
     */
    public void exportarAJSON() {
        List<Pelicula> lista_peliculas = findAll();

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File("peliculas.json"), lista_peliculas);
            System.out.println("Peliculas exportadas a peliculas.json");

        } catch (IOException e) {
            System.out.println("Error al exportar las peliculas a JSON: " + e.getMessage());
        }
    }
}