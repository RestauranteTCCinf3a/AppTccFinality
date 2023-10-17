package com.example.apprestaurant.model;

import java.io.Serializable;

public class CardapioModel implements Serializable {
   private double preco;

   private int id;
    private String nome;
    private String descricao;

    private byte[] imagem;

    private int numeroNoCarrinho;

    private String situacao;


    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public CardapioModel(int id, double preco, String nome, String descricao, byte[] imagem, String situacao) {
        this.id = id;
        this.preco = preco;
        this.nome = nome;
        this.descricao = descricao;
        this.imagem = imagem;
        this.situacao = situacao;
    }

    public CardapioModel(double preco, String nome, String descricao) {
        this.preco = preco;
        this.nome = nome;
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getNumeroNoCarrinho() {
        return numeroNoCarrinho;
    }

    public void setNumeroNoCarrinho(int numeroNoCarrinho) {
        this.numeroNoCarrinho = numeroNoCarrinho;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
