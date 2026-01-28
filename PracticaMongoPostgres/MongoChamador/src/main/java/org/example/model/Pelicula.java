package org.example.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection="peliculas")
public class Pelicula {

    @Id
    private Integer peliculaId;
    private String titulo;
    private String xenero;
    private Integer ano;
    private List<Actor> actores;

    public Integer getPeliculaId() {
        return peliculaId;
    }
    public void setPeliculaId(Integer peliculaId) {
        this.peliculaId = peliculaId;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getXenero() {
        return xenero;
    }
    public void setXenero(String xenero) {
        this.xenero = xenero;
    }
    public Integer getAno() {
        return ano;
    }
    public void setAno(Integer ano) {
        this.ano = ano;
    }
    public List<Actor> getActores() {
        return actores;
    }
    public void setActores(List<Actor> actores) {
        this.actores = actores;
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "peliculaId=" + peliculaId +
                ", titulo='" + titulo + '\'' +
                ", xenero='" + xenero + '\'' +
                ", ano=" + ano +
                ", actores=" + actores +
                '}';
    }
}
