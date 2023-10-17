package com.example.apprestaurant.model;

public class FuncionarioModel {
    private String nome;
    private String ocupacao;

    private String situacao;

    public FuncionarioModel(String nome, String ocupacao, String situacao) {
        this.nome = nome;
        this.ocupacao = ocupacao;
        this.situacao = situacao;
    }

    public String getNome() {
        return nome;
    }

    public String getOcupacao() {
        return ocupacao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
