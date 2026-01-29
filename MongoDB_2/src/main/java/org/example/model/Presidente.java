package org.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "presidentes")
public class Presidente {

    @Id
    private String id;
    private String nome;
    private int idade;
    private String partido;

    public Presidente(){}

    public Presidente(String id, String nome, int idade, String partido) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.partido = partido;
    }

    // Getters e Setters
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

    public int getIdade() {
        return idade;
    }
    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getPartido() {
        return partido;
    }
    public void setPartido(String partido) {
        this.partido = partido;
    }
    @Override
    public String toString() {
        return "Presidente{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                ", partido='" + partido + '\'' +
                '}';
    }
}



