package com.example.apprestaurant.controller;

import android.content.Context;

import com.example.apprestaurant.conexao.MSSQLConnection;
import com.example.apprestaurant.model.SignInModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignInController {

    public SignInModel login(SignInModel dadosUsuario, Context context) {
        // Objeto que irá armazenar os dados do usuário autenticado
        SignInModel dadosRetornados = null;

        // Tentativa de conectar ao banco de dados
        try {
            // Obtém a conexão com o banco de dados usando o objeto Context fornecido
            Connection conn = MSSQLConnection.getConnection(context);

            // Cria uma instrução SQL parametrizada para selecionar usuário, senha e status na tabela 'funcionario'
            // de acordo com o nome de usuário e senha fornecidos no objeto dadosUsuario
            PreparedStatement pst = conn.prepareStatement("SELECT usuario, senha, nome_func, ocupacao, ID_FUNC, situacao FROM funcionario " +
                    "WHERE usuario = ? AND senha = ? AND situacao = 'ATIVO'");
            pst.setString(1, dadosUsuario.getUsuario());
            pst.setString(2, dadosUsuario.getSenha());

            // Executa a consulta no banco de dados e obtém os resultados
            ResultSet res = pst.executeQuery();

            // Verifica se há um resultado na consulta (espera-se apenas um)
            while (res.next()) {
                // Se houver resultado, cria um novo objeto SignInModel com os dados do usuário autenticado
                dadosRetornados = new SignInModel(
                        res.getString("usuario"),
                        res.getString("senha"),
                        res.getString("nome_func"),
                        res.getString("ocupacao"),
                        res.getInt("ID_FUNC")
                );
            }

        } catch (SQLException e) {
            // Se ocorrer uma exceção SQL, significa que os dados não foram encontrados ou houve um erro no banco de dados
            // Nesse caso, cria um objeto SignInModel com uma mensagem de erro
            dadosRetornados = new SignInModel("Dados não encontrados", "");
        }

        // Retorna os dados do usuário autenticado ou um objeto SignInModel com mensagem de erro, se aplicável
        return dadosRetornados;
    }
}
