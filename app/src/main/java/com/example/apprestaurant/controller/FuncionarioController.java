package com.example.apprestaurant.controller;

import android.content.Context;

import com.example.apprestaurant.conexao.MSSQLConnection;
import com.example.apprestaurant.model.FuncionarioModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FuncionarioController {

    public FuncionarioModel getFuncionarioById(int idFuncionario, Context context) {
        FuncionarioModel funcionario = null;

        try {
            Connection conn = MSSQLConnection.getConnection(context);
            PreparedStatement pst = conn.prepareStatement("SELECT NOME_FUNC, OCUPACAO, situacao FROM funcionario " +
                    "WHERE ID_FUNC = ?");
            pst.setInt(1, idFuncionario);

            ResultSet res = pst.executeQuery();

            if (res.next()) {
                funcionario = new FuncionarioModel(
                        res.getString("NOME_FUNC"),
                        res.getString("OCUPACAO"),
                        res.getString("situacao")
                );
            }

            conn.close(); // Fechar a conexão após a consulta

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return funcionario;
    }
}



