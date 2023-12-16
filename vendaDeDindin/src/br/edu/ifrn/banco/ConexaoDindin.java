package br.edu.ifrn.banco;

import br.edu.ifrn.dominio.Dindin;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ConexaoDindin extends ConexaoBD {
    
    public Dindin selectDindin(String sabor) {
        conectar();
        
        Dindin d = new Dindin();
        
        try {
            instrucao = con.prepareStatement("select * from dindin where sabor = ? limit 1");
            instrucao.setString(1, sabor);

            ResultSet resul = instrucao.executeQuery();
            
            while (resul.next()) {                
                d.setSabor(resul.getString("sabor"));
                d.setCusto(resul.getDouble("custo"));
                d.setValor(resul.getDouble("valor"));
                d.setQuantidadeEstoque(resul.getInt("quantidadeEstoque"));
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar [unico]");
        } finally {
            fecharConexao();
        }
            
        return d;
    }
    
    public ArrayList<Dindin> selectDindins() {
        conectar();
        
        ArrayList<Dindin> lista = new ArrayList<>();
        
        try {
            instrucao = con.prepareStatement("select * from dindin");
            ResultSet resul = instrucao.executeQuery();
            
            while (resul.next()) {                
                
                Dindin d = new Dindin();
                
                d.setSabor(resul.getString("sabor"));
                d.setCusto(resul.getDouble("custo"));
                d.setValor(resul.getDouble("valor"));
                d.setQuantidadeEstoque(resul.getInt("quantidadeEstoque"));
                
                lista.add(d);
                
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar");
        } finally {
            fecharConexao();
        }
        
        return lista;
        
    }
    
}
