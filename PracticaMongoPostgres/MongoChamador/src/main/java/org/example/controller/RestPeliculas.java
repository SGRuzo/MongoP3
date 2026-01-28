package org.example.controller;

import org.example.model.Pelicula;
import org.example.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @RestController: Combina @Controller y @ResponseBody.
 * Todos los métodos devolverán datos (generalmente JSON) directamente al cuerpo de la respuesta.
 */
@RestController
@RequestMapping("/peliculas")
public class RestPeliculas {

    /**
     * @Autowired: Inyecta el bean del repositorio.
     * Nota: En el Service usamos inyección por constructor, aquí usamos @Autowired.
     * Ambos son válidos, pero el constructor es preferible para testeo.
     */
    @Autowired
    private PeliculaRepository peliculaRepository;

    /**
     * @PostMapping: Crea un nuevo recurso.
     * @RequestBody: Indica que el objeto 'pelicula' viene en el cuerpo de la petición (JSON).
     */
    @PostMapping
    public Pelicula create(@RequestBody Pelicula pelicula) {

        return peliculaRepository.save(pelicula);
    }

    /**
     * @GetMapping("/{id}"): El {id} es una variable de ruta.
     * @PathVariable: Mapea el valor de la URL al parámetro 'id'.
     */
    @GetMapping("/{id}")
    public Pelicula obtenerPorId(@PathVariable Integer id) {
        return peliculaRepository.findById(id).orElse(null);
    }

    /**
     * @RequestParam: Se usa para parámetros en la URL tipo ?titulo=Avatar.
     */
    @GetMapping("/buscar")
    public Pelicula getByTitulo(@RequestParam String titulo) {
        List<Pelicula> lista = peliculaRepository.findByTitulo(titulo);
        return lista.isEmpty() ? null : lista.get(0);
    }

}