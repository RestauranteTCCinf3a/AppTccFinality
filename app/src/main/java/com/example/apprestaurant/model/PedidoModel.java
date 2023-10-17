package com.example.apprestaurant.model;

public class PedidoModel {

    private int id;
    private int mesaId;
    private int cardapioId;
    private double total;

    private String descricao;

    private int quantidade;

    private String situacao;

    private int funcionarioId;

    public PedidoModel(int id, int mesaId, int cardapioId, double total, String descricao, int quantidade, String situacao, int funcionarioId) {
        this.id = id;
        this.mesaId = mesaId;
        this.cardapioId = cardapioId;
        this.total = total;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.situacao = situacao;
        this.funcionarioId = funcionarioId;
}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMesaId() {
        return mesaId;
    }

    public void setMesaId(int mesaId) {
        this.mesaId = mesaId;
    }

    public int getCardapioId() {
        return cardapioId;
    }

    public void setCardapioId(int cardapioId) {
        this.cardapioId = cardapioId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public int getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(int funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

}
