package org.example.controller;

import org.example.model.Adestrador;
import org.example.service.AdestradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(RestAdestrador.MAPPING)
public class RestAdestrador {

    public static final String MAPPING = "/mongodb/adestrador";

    @Autowired
    private AdestradorService adestradorService;

    @PostMapping("/gardar")
    public ResponseEntity<Adestrador> gardar(@RequestBody Adestrador adestrador) {
        adestradorService.crearAdestrador(adestrador);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<Adestrador>> listarTodos() {
        List<Adestrador> adestradores = adestradorService.buscarAdestradores();
        return new ResponseEntity<>(adestradores, HttpStatus.OK);
    }

    @PostMapping("/importarJSON")
    public ResponseEntity<List<Adestrador>> importarJSON() {
        try {
            List<Adestrador> list_adestrador = adestradorService.importarDesdeJSON("adestradores.json");
            return ResponseEntity.ok(list_adestrador);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

