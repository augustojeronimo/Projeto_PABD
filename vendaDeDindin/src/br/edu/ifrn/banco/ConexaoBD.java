package br.edu.ifrn.banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

abstract class ConexaoBD {
    
    protected Connection con;
    protected PreparedStatement instrucao;

    
    protected int conectar() {
        int mensagem = AcessoBD.OPERACAO_CONCLUIDA;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vendadindin","root","");
        } catch (ClassNotFoundException ex) {
            mensagem = AcessoBD.ERRO_DRIVER;
        } catch (SQLException ex) {
            mensagem = AcessoBD.ERRO_CONECTAR;
        }
        
        return mensagem;
    }
    
    protected int fecharConexao() {
        int mensagem = AcessoBD.OPERACAO_CONCLUIDA;
        
        try {
            if (instrucao != null) {
                instrucao.close();
            }
            
            con.close();
        } catch (SQLException ex) {
            mensagem = AcessoBD.ERRO_FECHAR_CONEXAO;
        }
        
        return mensagem;
    }
}
