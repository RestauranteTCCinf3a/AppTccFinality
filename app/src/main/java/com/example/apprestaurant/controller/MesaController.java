package com.example.apprestaurant.controller;

import android.content.Context;
import com.example.apprestaurant.conexao.MSSQLConnection;
import com.example.apprestaurant.model.MesaModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MesaController {
    public List<MesaModel> getMesasAtivas(Context context) {
        List<MesaModel> mesasAtivas = new ArrayList<>();

        try {
            Connection conn = MSSQLConnection.getConnection(context);
            PreparedStatement pst = conn.prepareStatement("SELECT id_mesa, numero_mesa, situacao FROM mesas WHERE situacao = 'ATIVO'");
            ResultSet res = pst.executeQuery();

            while (res.next()) {
                MesaModel mesa = new MesaModel(
                        res.getInt("id_mesa"),
                        res.getInt("numero_mesa"),
                        res.getString("situacao"));
                mesasAtivas.add(mesa);
            }

            conn.close(); // Fechar a conexão após a consulta

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mesasAtivas;
    }

    public int getIdMesaPorNumero(Context context, String numeroMesa) {
        int mesaId = -1; // Valor padrão se o número de mesa não for encontrado

        try {
            Connection conn = MSSQLConnection.getConnection(context);
            String query = "SELECT id_mesa FROM mesas WHERE numero_mesa = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, numeroMesa);
            ResultSet res = pst.executeQuery();

            if (res.next()) {
                mesaId = res.getInt("id_mesa");
            }

            conn.close(); // Fechar a conexão após a consulta

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mesaId;
    }
}
