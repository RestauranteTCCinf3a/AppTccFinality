package com.example.apprestaurant.controller;

import android.content.Context;
import android.widget.Toast;

import com.example.apprestaurant.conexao.MSSQLConnection;
import com.example.apprestaurant.model.PedidoModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoController {

    private Context context;

    public PedidoController(Context context) {
        this.context = context;
    }

    // Método para adicionar um novo pedido ao banco de dados
    public boolean adicionarPedido(PedidoModel pedido) {
        try {
            Connection conn = MSSQLConnection.getConnection(context);

            if (conn == null) {
                Toast.makeText(context, "Falha ao conectar ao banco de dados", Toast.LENGTH_SHORT).show();
                return false;
            }

            // Monta a query SQL para inserir o pedido na tabela 'pedidos'
            String query = "INSERT INTO Pedidos (id_mesa, ID_CARDAPIO, valorTotal, descricao, quantidade, situacao, ID_FUNC) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, pedido.getMesaId());
            pst.setInt(2, pedido.getCardapioId());
            pst.setDouble(3, pedido.getTotal());
            pst.setString(4, pedido.getDescricao());
            pst.setInt(5, pedido.getQuantidade());
            pst.setString(6, pedido.getSituacao());
            pst.setInt(7, pedido.getFuncionarioId());
            int rowsAffected = pst.executeUpdate();
            conn.close();

            if (rowsAffected > 0) {
                Toast.makeText(context, "Pedido adicionado com sucesso", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "Falha ao adicionar pedido", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (SQLException e) {
            Toast.makeText(context, "Erro SQL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        }
    }

    // Método para obter todos os pedidos do banco de dados
    public List<PedidoModel> obterTodosPedidos() {
        List<PedidoModel> listaPedidos = new ArrayList<>();
        try {
            Connection conn = MSSQLConnection.getConnection(context);

            if (conn == null) {
                Toast.makeText(context, "Falha ao conectar ao banco de dados", Toast.LENGTH_SHORT).show();
                return listaPedidos;
            }

            // Monta a query SQL para selecionar todos os pedidos da tabela 'pedidos'
            String query = "SELECT ID_Pedido, id_mesa, ID_CARDAPIO, valorTotal, descricao, quantidade, situacao, ID_FUNC FROM Pedidos";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ID_Pedido");
                int mesaId = rs.getInt("id_mesa");
                int cardapioId = rs.getInt("ID_CARDAPIO");
                double total = rs.getDouble("valorTotal");
                String descricao = rs.getString("descricao");
                int quantidade = rs.getInt("quantidade");
                String situacao = rs.getString("situacao");
                int funcionarioId = rs.getInt("ID_FUNC");

                PedidoModel pedido = new PedidoModel(id, mesaId, cardapioId, total, descricao, quantidade, situacao, funcionarioId);
                listaPedidos.add(pedido);
            }

            conn.close();
        } catch (SQLException e) {
            Toast.makeText(context, "Erro SQL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return listaPedidos;
    }

}
