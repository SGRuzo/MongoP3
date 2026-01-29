package org.example;

import org.example.model.Pelicula;
import org.example.service.Conexion;
import org.example.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Marca esta clase como un servicio (lógica de negocio)
@Service
public class Secuencia {

    // Servicio para conectar con la API REST de PostgreSQL
    private final Conexion conexion;
    // Servicio para gestionar películas en MongoDB
    private final PeliculaService peliculaService;

    // Constructor: inyecta los dos servicios
    @Autowired
    public Secuencia(Conexion conexion, PeliculaService peliculaService) {
        this.conexion = conexion;
        this.peliculaService = peliculaService;
    }

    // Método principal: ejecuta el flujo completo del programa
    public void executar() {
        // 1. Obtiene una película de PostgreSQL por su ID (ID 9)
        Pelicula peliPorId = conexion.getAPeliculaById(9l);
        // 2. Obtiene películas de PostgreSQL por su título
        List<Pelicula> peliPorTitulo = conexion.getPeliculaByTitulo("Top Gun");

        // 3. Guarda la película obtenida por ID en MongoDB
        peliculaService.crearPelicula(peliPorId);
        // 4. Guarda la primera película de la búsqueda por título en MongoDB
        peliculaService.crearPelicula(peliPorTitulo.get(0));

        // 5. Exporta todas las películas de MongoDB a un archivo JSON
        peliculaService.exportarAJSON();

    }
}