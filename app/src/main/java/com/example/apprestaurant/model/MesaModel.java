package com.example.apprestaurant.model;

public class MesaModel {

    private int id;

    private int numeroMesa;

    private String situacao;



    public MesaModel(int id, int numeroMesa, String situacao) {
        this.id = id;
        this.numeroMesa = numeroMesa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeromesa() {
        return numeroMesa;
    }

    public void setNumeromesa(int numero_mesa) {
        this.numeroMesa = numero_mesa;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}


