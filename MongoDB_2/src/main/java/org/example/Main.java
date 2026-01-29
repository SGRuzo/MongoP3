package org.example;


import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"org.example"})
public class Main {
    private final Secuencia secuencia;

    public Main(Secuencia secuencia) {
        this.secuencia = secuencia;
    }

    @PostConstruct
    public void ejecutarSolucion() {
        try {
            secuencia.exameSolucion();
        } catch (Exception e) {
            System.err.println("Error durante la ejecución: " + e.getMessage());
            e.printStackTrace();
        }
        // System.exit(200); // comentado para no forzar la salida del proceso durante pruebas
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
