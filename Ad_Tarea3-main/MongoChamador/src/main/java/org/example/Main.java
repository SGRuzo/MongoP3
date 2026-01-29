package org.example;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Marca esta clase como la aplicación principal de Spring Boot
@SpringBootApplication
public class Main {
    // Inyecta el servicio Secuencia (se ejecutará automáticamente)
    private final Secuencia secuencia;

    // Constructor: Spring inyecta el servicio Secuencia aquí
    public Main(Secuencia secuencia) {
        this.secuencia = secuencia;
    }

    // @PostConstruct: se ejecuta DESPUÉS de que la aplicación inicia
    // Aquí ejecutamos el flujo del programa
    @PostConstruct
    public void executar() {
        secuencia.executar();
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}