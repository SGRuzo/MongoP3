package org.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "paises")
public class Pais {

    @Id
    private String id;
    private String nome;
    private String organizacion;
    private List<String> partidos;
    private String presidenteId;

    public Pais() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(String organizacion) {
        this.organizacion = organizacion;
    }

    public List<String> getPartidos() {
        return partidos;
    }

    public void setPartidos(List<String> partidos) {
        this.partidos = partidos;
    }

    public String getIdPresidente() {
        return presidenteId;
    }

    public void setIdPresidente(String presidenteId) {
        this.presidenteId = presidenteId;
    }

    @Override
    public String toString() {
        return "Pais{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", organizacion='" + organizacion + '\'' +
                ", partidos=" + partidos +
                ", presidenteId='" + presidenteId + '\'' +
                '}';
    }
}