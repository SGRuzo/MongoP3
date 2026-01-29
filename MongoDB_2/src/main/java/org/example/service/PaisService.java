package org.example.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.model.Presidente;
import org.example.model.Pais;
import org.example.repository.PresidenteRepository;
import org.example.repository.PaisRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaisService {
    private final PaisRepository paisRepo;
    private final PresidenteRepository presidenteRepo;

    public PaisService(PaisRepository paisRepo, PresidenteRepository presidenteRepo) {
        this.paisRepo = paisRepo;
        this.presidenteRepo = presidenteRepo;
    }

    // INSERT - Crear un novo país
    public void criarPais(Pais pais) {
        paisRepo.save(pais);
    }

    // READ - Buscar un país por ID
    public Pais buscarPais(String id) {
        return paisRepo.findById(id).orElse(null);
    }

    // READ - Listar todos os países
    public List<Pais> listarPaises() {
        return paisRepo.findAll();
    }

    // UPDATE - Modificar un país
    public Pais modificarPais(String id, Pais paisActualizado) {
        Pais pais = paisRepo.findById(id).orElse(null);
        if (pais != null) {
            if (paisActualizado.getNome() != null) {
                pais.setNome(paisActualizado.getNome());
            }
            if (paisActualizado.getOrganizacion() != null) {
                pais.setOrganizacion(paisActualizado.getOrganizacion());
            }
            if (paisActualizado.getPartidos() != null) {
                pais.setPartidos(paisActualizado.getPartidos());
            }
            if (paisActualizado.getIdPresidente() != null) {
                pais.setIdPresidente(paisActualizado.getIdPresidente());
            }
            paisRepo.save(pais);
        }
        return pais;
    }

    // DELETE - Eliminar un país
    public boolean eliminarPais(String id) {
        if (paisRepo.existsById(id)) {
            paisRepo.deleteById(id);
            return true;
        }
        return false;
    }

    // DELETE - Eliminar todos os países
    public void borrarPasises() {
        paisRepo.deleteAll();
    }

    // UPDATE - Modificar a organización dun país
    public Pais modificarOrganizacionPais(String id, String novaOrganizacion) {
        Pais pais = paisRepo.findById(id).orElse(null);
        if (pais != null) {
            pais.setOrganizacion(novaOrganizacion);
            paisRepo.save(pais);
        }
        return pais;
    }

    // ...existing code...
    public Presidente buscarPresidenteDePais(String idPais) {
        Pais pais = buscarPais(idPais);
        if (pais == null) return null;
        Presidente presidente = presidenteRepo.findById(pais.getIdPresidente()).orElse(null);

        return presidente;
    }

    // Importar países desde JSON
    public List<Pais> importarDesdeJSON(String ruta) throws IOException {
        try(Reader reader = new InputStreamReader(new ClassPathResource(ruta).getInputStream());){
            Type listType = new TypeToken<ArrayList<Pais>>() {}.getType();
            List<Pais> listaPaises = new Gson().fromJson(reader, listType);

            return paisRepo.saveAll(listaPaises);
        }
    }

}
