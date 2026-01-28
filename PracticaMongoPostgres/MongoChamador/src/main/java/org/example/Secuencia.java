package org.example;

import org.example.model.Pelicula;
import org.example.service.ConexionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Secuencia {

    private final ConexionService conexionService;


    @Autowired
    public Secuencia(ConexionService conexionService) {
        this.conexionService = conexionService;
    }

    public void executar() {

       List<Pelicula> peliculas = conexionService.getAllPeliculas();

    }
}
