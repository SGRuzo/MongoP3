package org.example.controller;

import org.example.model.Pelicula;
import org.example.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RestPeliculas.MAPPING)
public class RestPeliculas {

    public static final String MAPPING = "/postgres/peliculas";

    @Autowired
    private PeliculaService peliculaService;

    @GetMapping
    public List<Pelicula> getAll() {
        return peliculaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pelicula> getById(@PathVariable Long id) {
        return peliculaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<Pelicula>> getByTitulo(@PathVariable String titulo) {
        List<Pelicula> resultados = peliculaService.findByTitulo(titulo);
        if (resultados == null || resultados.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultados);
    }

    @PostMapping
    public ResponseEntity<Pelicula> create(@RequestBody Pelicula pelicula) {
        Pelicula gardada = peliculaService.save(pelicula);
        return ResponseEntity.ok(gardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pelicula> update(@PathVariable Long id,
                                         @RequestBody Pelicula datos) {

        return peliculaService.findById(id)
                .map(p -> {
                    p.setTitulo(datos.getTitulo());
                    p.setAno(datos.getAno());
                    p.setXenero(datos.getXenero());
                    return ResponseEntity.ok(peliculaService.save(p));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!peliculaService.exists(id)) {
            return ResponseEntity.notFound().build();
        }
        peliculaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
