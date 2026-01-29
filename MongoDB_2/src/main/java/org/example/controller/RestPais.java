package org.example.controller;

import org.example.model.Presidente;
import org.example.model.Pais;
import org.example.service.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(RestPais.MAPPING)
public class RestPais {

    public static final String MAPPING = "/mongodb/paises";

    @Autowired
    private PaisService paisService;

    // CREATE - Crear un novo país
    @PostMapping("/criar")
    public ResponseEntity<Void> criarPais(@RequestBody Pais pais) {
        paisService.criarPais(pais);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // READ - Obter un país por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pais> obterPais(@PathVariable String id) {
        Pais pais = paisService.buscarPais(id);
        if (pais == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pais);
    }

    // READ - Listar todos los países
    @GetMapping("/listarTodos")
    public ResponseEntity<List<Pais>> listarPaises() {
        List<Pais> paises = paisService.listarPaises();
        return new ResponseEntity<>(paises, HttpStatus.OK);
    }

    // UPDATE - Modificar un país
    @PutMapping("/{id}")
    public ResponseEntity<Pais> modificarPais(@PathVariable String id, @RequestBody Pais paisActualizado) {
        Pais paisModificado = paisService.modificarPais(id, paisActualizado);
        if (paisModificado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(paisModificado);
    }

    // DELETE - Eliminar un país
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPais(@PathVariable String id) {
        if (paisService.eliminarPais(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Obter presidente de un país
    @GetMapping("/{id}/presidente")
    public ResponseEntity<Presidente> obterPresidentePais(@PathVariable String id) {
        Presidente presidente = paisService.buscarPresidenteDePais(id);
        if (presidente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(presidente);
    }

}

