package org.example.controller;

import org.example.model.Pelicula;
import org.example.model.Actor;
import org.example.service.PeliculaService;
import org.example.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RestActores.MAPPING)
public class RestActores {

    public static final String MAPPING = "/postgres/actores";

    @Autowired
    private PeliculaService peliculaService;
    @Autowired
    private ActorService actorService;


    @GetMapping
    public List<Actor> getAll() {
        return actorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actor> getById(@PathVariable Integer id) {
        return actorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Actor> create(@RequestBody Actor actor) { //recoger pelicula e meterla en cada actor
/*{
  "nome": "string",
  "apelidos": "string",
  "nacionalidade": "string",
  "pelicula": {
    "id": "1"
  }
}*/
        if (actor.getPelicula() != null && actor.getPelicula().getId() != null) {
            Pelicula pel = peliculaService.findById(actor.getPelicula().getId())
                    .orElse(null);
            if (pel == null) {
                return ResponseEntity.badRequest().build();
            }
            actor.setPelicula(pel);
        }
        Actor gardado = actorService.save(actor);
        return ResponseEntity.ok(gardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Actor> update(@PathVariable Integer id,
                                          @RequestBody Actor datos) {
        return actorService.findById(id)
                .map(a -> {
                    a.setNome(datos.getNome());
                    a.setApelidos(datos.getApelidos());
                    a.setNacionalidade(datos.getNacionalidade());

                    if (datos.getPelicula() != null && datos.getPelicula().getId() != null) {
                        Pelicula pel = peliculaService.findById(datos.getPelicula().getId())
                                .orElse(null);
                        a.setPelicula(pel);
                    }

                    return ResponseEntity.ok(actorService.save(a));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!actorService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        actorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
