package org.example.controller;

import org.example.model.Actor;
import org.example.service.ActorService;
import org.example.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RestActores.MAPPING)
public class RestActores {

    public static final String MAPPING = "/postgres/actores";

    @Autowired
    private ActorService actorService;
    @Autowired
    private PeliculaService peliculaService;

    @GetMapping
    public List<Actor> getAll() {
        return actorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actor> getById(@PathVariable Long id) {
        return actorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Actor> create(@RequestBody Actor actor) {
        if (actor.getPelicula() != null && actor.getPelicula().getId() != null) {
            var peli = peliculaService.findById(actor.getPelicula().getId())
                    .orElse(null);
            if (peli == null) {
                return ResponseEntity.badRequest().build();
            }
            actor.setPelicula(peli);
        }
        Actor gardado = actorService.save(actor);
        return ResponseEntity.ok(gardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Actor> update(@PathVariable Long id,
                                        @RequestBody Actor datos) {
        return actorService.findById(id)
                .map(a -> {
                    a.setNome(datos.getNome());
                    a.setApelidos(datos.getApelidos());
                    a.setNacionalidade(datos.getNacionalidade());

                    if (datos.getPelicula() != null && datos.getPelicula().getId() != null) {
                        var peli = peliculaService.findById(datos.getPelicula().getId())
                                .orElse(null);
                        if (peli != null) {
                            a.setPelicula(peli);
                        }
                    }
                    return ResponseEntity.ok(actorService.save(a));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!actorService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        actorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
