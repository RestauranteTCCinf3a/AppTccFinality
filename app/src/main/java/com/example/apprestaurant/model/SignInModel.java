package com.example.apprestaurant.model;

public class SignInModel {

    private String usuario;
    private String senha;

    private String nome;

    private String ocupacao;

    private int idFunc;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public SignInModel(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }

    public String getOcupacao() {
        return ocupacao;
    }

    public void setOcupacao(String ocupacao) {
        this.ocupacao = ocupacao;
    }

    public int getIdFunc() {
        return idFunc;
    }

    public void setIdFunc(int idFunc) {
        this.idFunc = idFunc;
    }

    public SignInModel(String usuario, String senha, String nome, String ocupacao) {
        this.usuario = usuario;
        this.senha = senha;
        this.nome = nome;
        this.ocupacao = ocupacao;

    }



    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    public SignInModel(String usuario, String senha, String nome, String ocupacao, int idFunc) {
        this.usuario = usuario;
        this.senha = senha;
        this.nome = nome;
        this.ocupacao = ocupacao;
        this.idFunc = idFunc;
    }
}
