package org.example.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="actores")
public class Actor {

    @Id
    private Integer actorId;
    private String nome;
    private String apelidos;
    private String nacionalidade;

    private Pelicula pelicula;

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
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

    @Override
    public String toString() {
        return "Actor{" +
                "actorId=" + actorId +
                ", nome='" + nome + '\'' +
                ", apelidos='" + apelidos + '\'' +
                ", nacionalidade='" + nacionalidade + '\'' +
                ", pelicula=" + pelicula +
                '}';
    }
}
