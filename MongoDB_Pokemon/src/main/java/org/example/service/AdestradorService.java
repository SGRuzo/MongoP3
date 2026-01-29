package org.example.service;

import org.example.model.Adestrador;
import org.example.repository.AdestradorRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

@Service
public class AdestradorService {

    private final AdestradorRepository adestradorRepo;

    public AdestradorService(AdestradorRepository adestradorRepo) {
        this.adestradorRepo = adestradorRepo;
    }

    public void crearAdestrador(Adestrador a) {
        adestradorRepo.save(a);
    }

    public Adestrador buscarAdestrador(String id) {
        return adestradorRepo.findById(id).orElse(null);
    }

    public List<Adestrador> buscarAdestradores() {
        return adestradorRepo.findAll();
    }

    public List<Adestrador> importarDesdeJSON(String ruta) throws IOException {
        try(Reader reader = new InputStreamReader(new ClassPathResource(ruta).getInputStream());){
            Type listType = new TypeToken<ArrayList<Adestrador>>() {}.getType();
            List<Adestrador> list_adestrador = new Gson().fromJson(reader, listType);

            return adestradorRepo.saveAll(list_adestrador);
        }
    }



}
