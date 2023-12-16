package br.edu.ifrn.banco;

import br.edu.ifrn.dominio.Dindin;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ConexaoDindin extends ConexaoBD {
    
    public void insert(Dindin d){
        conectar();
        
        try {
            instrucao = con.prepareStatement("insert into dindin values (?,?,?,?)");
            
            instrucao.setString(1, d.getSabor());
            instrucao.setDouble(2, d.getCusto());
            instrucao.setDouble(3, d.getValor());
            instrucao.setInt(4, d.getQuantidadeEstoque());
            
            instrucao.execute();
            
            JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar...");
        } finally {
            fecharConexao();
        }
    }
    
    public void update(Dindin d){
        conectar();
        
        try {
            instrucao = con.prepareStatement("update dindin set custo = ?, valor = ?, quantidadeEstoque = ? where sabor = ? limit 1");
            
            instrucao.setDouble(1, d.getCusto());
            instrucao.setDouble(2, d.getValor());
            instrucao.setInt(3, d.getQuantidadeEstoque());
            instrucao.setString(4, d.getSabor());
            
            instrucao.execute();
            
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar...");
        } finally {
            fecharConexao();
        }
    }
    
    public void delete(Dindin d){
        conectar();
        
        try {
            instrucao = con.prepareStatement("delete from dindin where sabor = ? limit 1");
            
            instrucao.setString(1, d.getSabor());
            
            instrucao.execute();
            
            JOptionPane.showMessageDialog(null, "Removido com sucesso!");
        } catch (SQLException ex) {
            
            if (ex.getMessage().contains("foreign key")) {
                JOptionPane.showMessageDialog(null, "Erro ao remover...\n[não é possível remover dindins que constam em vendas]");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao remover...");
            }
            
        } finally {
            fecharConexao();
        }
    }
    
    // Retorna um objeto de Dindin
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
    
    // Retorna um ArrayList de objetos de Dindin
    public ArrayList<Dindin> selectDindins() {
        conectar();
        
        ArrayList<Dindin> lista = new ArrayList<>();
        
        try {
            instrucao = con.prepareStatement("select * from dindin order by quantidadeEstoque desc");
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
