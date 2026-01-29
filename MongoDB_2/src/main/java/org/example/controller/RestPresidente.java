package org.example.controller;

import org.example.model.Presidente;
import org.example.service.PresidenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(RestPresidente.MAPPING)
public class RestPresidente {

    public static final String MAPPING = "/mongodb/presidentes";

    @Autowired
    private PresidenteService presidenteService;

    // CREATE - Crear un novo presidente
    @PostMapping("/criar")
    public ResponseEntity<Void> criarPresidente(@RequestBody Presidente presidente) {
        presidenteService.crearPresidente(presidente);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // READ - Obter un presidente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Presidente> obterPresidente(@PathVariable String id) {
        Presidente presidente = presidenteService.buscarPresidente(id);
        if (presidente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(presidente);
    }

    // READ - Listar todos os presidentes
    @GetMapping("/listarTodos")
    public ResponseEntity<List<Presidente>> listarPresidentes() {
        List<Presidente> presidentes = presidenteService.listarPresidentes();
        return new ResponseEntity<>(presidentes, HttpStatus.OK);
    }

    // UPDATE - Modificar un presidente (objeto completo)
    @PutMapping("/{id}/nombre/{nombre}")
    public ResponseEntity<Presidente> modificarNomePresidente(@PathVariable String id, @PathVariable String nombre) {
        Presidente presidenteModificado = presidenteService.modificarPresidente(id, nombre);
        if (presidenteModificado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(presidenteModificado);
    }

    // DELETE - Eliminar un presidente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPresidente(@PathVariable String id) {
        if (presidenteService.eliminarPresidente(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Importar presidentes desde JSON
    @PostMapping("/importarJSON")
    public ResponseEntity<List<Presidente>> importarJSON(@RequestParam String ruta) {
        try {
            List<Presidente> presidentes = presidenteService.importarDesdeJSON(ruta);
            return ResponseEntity.ok(presidentes);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}

