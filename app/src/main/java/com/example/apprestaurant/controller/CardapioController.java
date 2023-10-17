package com.example.apprestaurant.controller;

import android.content.Context;
import com.example.apprestaurant.conexao.MSSQLConnection;
import com.example.apprestaurant.model.CardapioModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardapioController {

    public List<CardapioModel> getCombosFromDatabase(Context context) {
        List<CardapioModel> combos = new ArrayList<>();

        try {
            Connection conn = MSSQLConnection.getConnection(context);
            PreparedStatement pst = conn.prepareStatement("SELECT ID_CARDAPIO, PRECO_CARDAPIO, NOME, DESCRICAO, imagem, situacao FROM cardapio");
            ResultSet res = pst.executeQuery();

            while (res.next()) {
                int id = res.getInt("ID_CARDAPIO");
                double preco = res.getFloat("PRECO_CARDAPIO");
                String nome = res.getString("NOME");
                String descricao = res.getString("DESCRICAO");
                byte[] imagem = res.getBytes("imagem");
                String situacao = res.getString("situacao");

                CardapioModel combo = new CardapioModel(id, preco, nome, descricao, imagem, situacao);
                combos.add(combo);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return combos;
    }

    public int getIdCardapioPorNomeItem(Context context, String nome) {
        int id = -1;

        try {
            Connection conn = MSSQLConnection.getConnection(context);
            String query = "SELECT ID_CARDAPIO FROM cardapio WHERE NOME = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, nome);
            ResultSet res = pst.executeQuery();

            if (res.next()) {
                id = res.getInt("ID_CARDAPIO");
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    public List<CardapioModel> getCombosAtivosFromDatabase(Context context) {
        List<CardapioModel> combos = new ArrayList<>();

        try {
            Connection conn = MSSQLConnection.getConnection(context);
            PreparedStatement pst = conn.prepareStatement("SELECT ID_CARDAPIO, PRECO_CARDAPIO, NOME, DESCRICAO, imagem, situacao FROM cardapio WHERE situacao = 'ATIVO'");
            ResultSet res = pst.executeQuery();

            while (res.next()) {
                int id = res.getInt("ID_CARDAPIO");
                double preco = res.getFloat("PRECO_CARDAPIO");
                String nome = res.getString("NOME");
                String descricao = res.getString("DESCRICAO");
                byte[] imagem = res.getBytes("imagem");
                String situacao = res.getString("situacao");

                CardapioModel combo = new CardapioModel(id, preco, nome, descricao, imagem, situacao);
                combos.add(combo);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return combos;
    }

    // Você pode adicionar mais métodos aqui, se necessário
}
