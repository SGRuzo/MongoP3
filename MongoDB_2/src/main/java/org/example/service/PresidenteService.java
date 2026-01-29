package org.example.service;

import org.example.model.Presidente;
import org.example.repository.PresidenteRepository;
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
public class PresidenteService {

    private final PresidenteRepository repository;

    public PresidenteService(PresidenteRepository repository) {
        this.repository = repository;
    }

    // INSERT - Crear un novo presidente
    public void criarPresidente(Presidente presidente) {
        repository.save(presidente);
    }

    // READ - Buscar un presidente por ID
    public Presidente buscarPresidente(String id) {
        return repository.findById(id).orElse(null);
    }

    // READ - Listar todos os presidentes
    public List<Presidente> listarPresidentes() {
        return repository.findAll();
    }

    // UPDATE - Modificar un presidente
    public Presidente modificarPresidente(String id, Presidente presidenteActualizado) {
        Presidente presidente = repository.findById(id).orElse(null);
        if (presidente != null) {
            if (presidenteActualizado.getNome() != null) {
                presidente.setNome(presidenteActualizado.getNome());
            }
            if (presidenteActualizado.getIdade() > 0) {
                presidente.setIdade(presidenteActualizado.getIdade());
            }
            if (presidenteActualizado.getPartido() != null) {
                presidente.setPartido(presidenteActualizado.getPartido());
            }
            repository.save(presidente);
        }
        return presidente;
    }

    // DELETE - Eliminar un presidente
    public boolean eliminarPresidente(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // Importar desde JSON
    public List<Presidente> importarDesdeJSON(String ruta) throws IOException {
        try(Reader reader = new InputStreamReader(new ClassPathResource(ruta).getInputStream());){
            Type listType = new TypeToken<ArrayList<Presidente>>() {}.getType();
            List<Presidente> presidentesList = new Gson().fromJson(reader, listType);

            return repository.saveAll(presidentesList);
        }
    }


}
