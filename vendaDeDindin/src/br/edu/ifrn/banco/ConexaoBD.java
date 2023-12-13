package br.edu.ifrn.banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

abstract class ConexaoBD {
    
    Connection con;
    PreparedStatement instrucao;

    
    protected void conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vendadindin","root","");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar driver");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar conectar");
        }
    }
    
    protected void fecharConexao() {
        try {
            instrucao.close();
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao fechar conexão");
        }
    }
    
}
