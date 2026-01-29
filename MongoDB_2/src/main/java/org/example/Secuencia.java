package org.example;

import org.example.model.Presidente;
import org.example.model.Pais;
import org.example.service.PresidenteService;
import org.example.service.PaisService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Secuencia {

    private final PaisService paisService;
    private final PresidenteService presidenteService;

    public Secuencia(PaisService paisService, PresidenteService presidenteService) {
        this.paisService = paisService;
        this.presidenteService = presidenteService;
    }

    public void exameSolucion() {
        // 1. INSERCIÓN DE DATOS
        System.out.println("1. INSERCIÓN DE DATOS EN LA DB");

        // Crear presidentes
        Presidente presidente1 = new Presidente();
        presidente1.setId("pres1");
        presidente1.setNome("Carlos García");
        presidente1.setIdade(55);
        presidente1.setPartido("Partido Democrático");

        Presidente presidente2 = new Presidente();
        presidente2.setId("pres2");
        presidente2.setNome("María López");
        presidente2.setIdade(48);
        presidente2.setPartido("Partido Socialista");

        presidenteService.criarPresidente(presidente1);
        presidenteService.criarPresidente(presidente2);
        System.out.println("Presidentes creados: " + presidente1.getNome() + ", " + presidente2.getNome());

        // Crear países
        Pais pais1 = new Pais();
        pais1.setId("pais1");
        pais1.setNome("España");
        pais1.setOrganizacion("Monarquía Parlamentaria");
        pais1.setPartidos(List.of("PP", "PSOE", "VOX", "Podemos"));
        pais1.setIdPresidente("pres1");

        Pais pais2 = new Pais();
        pais2.setId("pais2");
        pais2.setNome("Portugal");
        pais2.setOrganizacion("República Democrática");
        pais2.setPartidos(List.of("PS", "PSD", "BE", "CDU"));
        pais2.setIdPresidente("pres2");

        paisService.criarPais(pais1);
        paisService.criarPais(pais2);
        System.out.println("✓ Países creados: " + pais1.getNome() + ", " + pais2.getNome());


        // 2. LECTURA DE DATOS
        System.out.println("2. LECTURA DE DATOS DE LA DB");

        List<Presidente> presidentes = presidenteService.listarPresidentes();
        System.out.println("Presidentes en la BD:");
        presidentes.forEach(p -> System.out.println("  - " + p.getNome() + " (" + p.getPartido() + ")"));

        List<Pais> paises = paisService.listarPaises();
        System.out.println("Países en la BD:");
        paises.forEach(p -> System.out.println("  - " + p.getNome() + " (" + p.getOrganizacion() + ")"));

        // 3. MODIFICACIÓN DE DATOS
        System.out.println("3. MODIFICACIÓN DE DATOS DE LA DB");

        Pais paisActualizado = new Pais();
        paisActualizado.setNome("España (Reino Unido)");
        paisActualizado.setOrganizacion("Monarquía Parlamentaria Constitucional");
        paisActualizado.setPartidos(List.of("PP", "PSOE", "Ciudadanos", "VOX"));

        paisService.modificarPais("pais1", paisActualizado);
        System.out.println("País 'España' modificado");

        Presidente presidenteActualizado = new Presidente();
        presidenteActualizado.setNome("Carlos García Fernández");
        presidenteActualizado.setIdade(56);
        presidenteActualizado.setPartido("Partido Democrático Renovado");

        presidenteService.modificarPresidente("pres1", presidenteActualizado);
        System.out.println("Presidente 'Carlos García' modificado");


        // 4. LECTURA DE DATOS MODIFICADOS
        System.out.println("4. LECTURA DE DATOS MODIFICADOS");

        Pais paisModificado = paisService.buscarPais("pais1");
        System.out.println("Datos de España (modificado):");
        System.out.println("  - Nombre: " + paisModificado.getNome());
        System.out.println("  - Organización: " + paisModificado.getOrganizacion());
        System.out.println("  - Partidos: " + paisModificado.getPartidos());

        Presidente presidenteModificado = presidenteService.buscarPresidente("pres1");
        System.out.println("Datos del Presidente (modificado):");
        System.out.println("  - Nombre: " + presidenteModificado.getNome());
        System.out.println("  - Edad: " + presidenteModificado.getIdade());
        System.out.println("  - Partido: " + presidenteModificado.getPartido());


        // 5. BÚSQUEDA DE RELACIONES
        System.out.println("5. BÚSQUEDA DE RELACIONES (Presidente de un País)");

        Presidente presidenteDelPais = paisService.buscarPresidenteDePais("pais1");
        System.out.println("Presidente de España: " + presidenteDelPais.getNome());


        // 6. ELIMINACIÓN DE DATOS
        System.out.println("6. ELIMINACIÓN DE DATOS DE LA DB");

        boolean eliminadoPais = paisService.eliminarPais("pais2");
        boolean eliminadoPresidente = presidenteService.eliminarPresidente("pres2");

        if (eliminadoPais) {
            System.out.println("País 'Portugal' eliminado");
        }
        if (eliminadoPresidente) {
            System.out.println("Presidente 'María López' eliminado");
        }


        // 7. VERIFICACIÓN FINAL
        System.out.println("7. VERIFICACIÓN FINAL");

        List<Pais> paisesFinales = paisService.listarPaises();
        List<Presidente> presidentesFinales = presidenteService.listarPresidentes();

        System.out.println("Paises restantes: " + paisesFinales.size());
        paisesFinales.forEach(p -> System.out.println("  - " + p.getNome()));

        System.out.println("Presidentes restantes: " + presidentesFinales.size());
        presidentesFinales.forEach(p -> System.out.println("  - " + p.getNome()));

    }
}



