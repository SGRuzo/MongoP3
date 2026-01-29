package org.example.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "actores")
@Schema(description = "Modelo de un actor")
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idactor")
    @Schema(description = "ID del actor", example = "1")
    private Long id;

    @Column(length = 100)
    @Schema(description = "Nombre del actor", example = "Tom Hanks")
    private String nome;

    @Column(length = 100)
    @Schema(description = "Apellidos del actor", example = "Hanks")
    private String apelidos;

    @Column(length = 100)
    @Schema(description = "Nacionalidad del actor", example = "USA")
    private String nacionalidade;

    @ManyToOne
    @JoinColumn(name = "idpelicula")
    @JsonBackReference
    private Pelicula pelicula;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelidos() {
        return apelidos;
    }

    public void setApelidos(String apelidos) {
        this.apelidos = apelidos;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    @JsonProperty("idpelicula")
    public Long getIdpelicula() {
        return pelicula != null ? pelicula.getId() : null;
    }

    @JsonProperty("idpelicula")
    public void setIdpelicula(Long idpelicula) {
        if (idpelicula != null) {
            Pelicula p = new Pelicula();
            p.setId(idpelicula);
            this.pelicula = p;
        }
    }
}
