package org.example;

import org.example.service.PresidenteService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Secuencia {

    private final PresidenteService presidenteService;

    public Secuencia(PresidenteService presidenteService) {
        this.presidenteService = presidenteService;
    }

    public void executar() {
        presidenteService.borrarPresidentes();

        System.out.println("Importar datos desde JSON:");
        try {
            var presidentesImportados = presidenteService.importarDesdeJSON("presidentes.json");
            System.out.println("Total presidentes importados: " + presidentesImportados.size());
            for (var presidente : presidentesImportados) {
                System.out.println("  ID: " + presidente.getId() + ", Nombre: " + presidente.getNome() +
                                 ", Edad: " + presidente.getIdade() + ", Partido: " + presidente.getPartido());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Mostrar datos");
        var presidentesList = presidenteService.listarPresidentes();
        System.out.println("Total de presidentes: " + presidentesList.size());
        for (var presidente : presidentesList) {
            System.out.println("  ID: " + presidente.getId() + ", Nombre: " + presidente.getNome() +
                             ", Edad: " + presidente.getIdade() + ", Partido: " + presidente.getPartido());
        }

        System.out.println("Modificar datos");
        var presidenteModificado = presidenteService.modificarPresidente("22f", "Paquito");
        System.out.println("Presidente modificado: " + presidenteModificado);


        System.out.println("Eliminar datos");
        presidenteService.borrarPresidentes();

        System.out.println("Mostrar datos despues de eliminar todo");
        var presidentesFinal = presidenteService.listarPresidentes();
        System.out.println("Total de presidentes finales: " + presidentesFinal.size());
        for (var presidente : presidentesFinal) {
            System.out.println("  ID: " + presidente.getId() + ", Nombre: " + presidente.getNome() +
                             ", Edad: " + presidente.getIdade() + ", Partido: " + presidente.getPartido());
        }
    }
}
