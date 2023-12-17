package br.edu.ifrn.banco;

import br.edu.ifrn.dominio.Dindin;
import br.edu.ifrn.dominio.DindinVendido;
import br.edu.ifrn.dominio.Venda;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ConexaoVenda extends ConexaoBD {
    public ArrayList<Venda> select() {
        conectar();
        
        ArrayList<Venda> lista = new ArrayList<>();
        
        try {
            instrucao = con.prepareStatement("select * from venda");
            ResultSet resul = instrucao.executeQuery();
            
            while (resul.next()) {                
                
                Venda v = new Venda();
                
                v.setIdVenda(resul.getInt("idVenda"));
                v.setValorTotal(resul.getDouble("valorTotal"));
                v.setDesconto(resul.getDouble("desconto"));
                v.setDataVenda(resul.getDate("dataVenda"));
                v.setEstado(resul.getString("estado"));
                
                v.setDindinsVendidos(new SubInsert().selectDindinsVendidos(v.getIdVenda()));
                
                lista.add(v);
                System.out.println(v.getDindinsVendidos().toString());
                
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar...");
        } finally {
            fecharConexao();
        }
        
        return lista;
    }
    
    
    public void insert(Venda v){
        conectar();
        
        v.setIdVenda(new SubInsert().getNextID());
        
        try {
            instrucao = con.prepareStatement("insert into venda values (?,?,?,?,?)");
            
            instrucao.setDouble(1, v.getIdVenda());
            instrucao.setDouble(2, v.getValorTotal());
            instrucao.setDouble(3, v.getDesconto());
            instrucao.setDate(4, v.getDataVenda());
            instrucao.setString(5, v.getEstado());
            
            instrucao.execute();
            
            v.getDindinsVendidos().forEach((dv) -> {
                new SubInsert().insertDindinVendido(dv);
            });
            
            JOptionPane.showMessageDialog(null, "Venda realizada com sucesso!");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar venda...");
        } finally {
            fecharConexao();
        }
    }
    
    private class SubInsert extends ConexaoBD{
        
        private int getNextID() {
            conectar();
            
            int id = -1;
            
            try {
                instrucao = con.prepareStatement("select max(idVenda) as id from venda limit 1");
                ResultSet resul = instrucao.executeQuery();

                while (resul.next()) {                
                    id = (1 + resul.getInt("id"));
                }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                fecharConexao();
            }

            return id;
        }
        
        private void insertDindinVendido(DindinVendido dv){
            conectar();
            
            try {
                instrucao = con.prepareStatement("insert into dindinVendido values (?,?,?)");
                
                instrucao.setInt(1, dv.getIdVenda());
                instrucao.setString(2, dv.getDindin().getSabor());
                instrucao.setInt(3, dv.getQuantidade());

                instrucao.execute();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                fecharConexao();
            }
        }
        
        public ArrayList<DindinVendido> selectDindinsVendidos(int idVenda) {
            conectar();

            ArrayList<DindinVendido> lista = new ArrayList<>();

            try {
                instrucao = con.prepareStatement("select dv.idVenda, dv.saborDindin, dv.quantidade from dindinVendido as dv, venda as v where dv.idVenda = v.idVenda and v.idVenda = ?");
                
                instrucao.setInt(1, idVenda);
                
                ResultSet resul = instrucao.executeQuery();
                
                while (resul.next()) {                    
                    DindinVendido dv = new DindinVendido(new Dindin(), 0);
                    
                    dv.setIdVenda(resul.getInt("idVenda"));
                    dv.getDindin().setSabor(resul.getString("saborDindin"));
                    dv.setQuantidade(resul.getInt("quantidade"));
                    
                    lista.add(dv);
                }
                
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                fecharConexao();
            }

            return lista;
        }
    }
    
}
