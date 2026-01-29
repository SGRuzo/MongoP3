package org.example.model;

// Clase que representa un actor en MongoDB
public class Actor {

    private String idActor;
    private String nome;
    private String apelidos;
    private String nacionalidade;
    private Long idPelicula;

    public String getIdActor() {
        return idActor;
    }

    public void setIdActor(String idActor) {
        this.idActor = idActor;
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

    public Long getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Long idPelicula) {
        this.idPelicula = idPelicula;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "idActor=" + idActor +
                ", nome='" + nome + '\'' +
                ", apelidos='" + apelidos + '\'' +
                ", nacionalidade='" + nacionalidade + '\'' +
                ", pelicula=" + idPelicula +
                '}';
    }
}
