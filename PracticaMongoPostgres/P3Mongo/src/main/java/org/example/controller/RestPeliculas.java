package org.example.controller;

import org.example.model.Pelicula;
import org.example.model.Actor;
import org.example.service.PeliculaService;
import org.example.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(RestPeliculas.MAPPING)
public class RestPeliculas {

    public static final String MAPPING = "/postgres/peliculas";

    @Autowired
    private PeliculaService peliculaService;
    @Autowired
    private ActorService actorService;

    @GetMapping
    public List<Pelicula> getAll() {
        return peliculaService.obterTodasPeliculas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pelicula> getById(@PathVariable Integer id) {
        return peliculaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pelicula> create(@RequestBody Pelicula pelicula) { //acepta crear actores en pelicula porque se crea 1º pelicula
        Pelicula gardado = peliculaService.save(pelicula);
        return ResponseEntity.ok(gardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pelicula> update(@PathVariable Integer id,
                                         @RequestBody Pelicula datos) {
      /*  return peliculaService.findById(id)
                .map(e -> {
                    e.setTitulo(datos.getTitulo());
                    e.setXenero(datos.getXenero());
                    return ResponseEntity.ok(peliculaService.save(e));
                })
                .orElse(ResponseEntity.notFound().build());
        */

        var peliculaOptional= peliculaService.findById(id);
        if(peliculaOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Pelicula peliculaToUpdate = peliculaOptional.get();
        peliculaToUpdate.setXenero(datos.getXenero());
        peliculaToUpdate.setTitulo(datos.getTitulo());
        peliculaToUpdate.setAno(datos.getAno());
        peliculaToUpdate = peliculaService.save(peliculaToUpdate);

        return ResponseEntity.ok(peliculaToUpdate);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!peliculaService.existe(id)) {
            return ResponseEntity.notFound().build();
        }
        peliculaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
