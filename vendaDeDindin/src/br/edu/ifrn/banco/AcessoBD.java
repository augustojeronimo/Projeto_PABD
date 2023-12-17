package br.edu.ifrn.banco;

import br.edu.ifrn.dominio.Dindin;
import br.edu.ifrn.dominio.DindinVendido;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class AcessoBD {
    /* --- DINDIN --- */
    // insert dindin                (int)                       [ConexaoDindin]
    public int InsertDindin(Dindin d) {
        return new ConexaoDindin().insert(d);
    }
    // update dindin                (int)                       [ConexaoDindin]
    public int updateDindin(Dindin d) {
        return new ConexaoDindin().update(d);
    }
    // delete dindin                (int)                       [ConexaoDindin]
    public int deleteDindin(Dindin d) {
        return new ConexaoDindin().delete(d);
    }
    // select dindin                (Dindin)                    [ConexaoDindin]
    public Dindin InsertDindin(String sabor) {
        return new ConexaoDindin().selectDindin(sabor);
    }
    // select dindins               (ArrayList<Dindin>)         [ConexaoDindin]
    public ArrayList<Dindin> InsertDindin() {
        return new ConexaoDindin().selectDindins();
    }
    
    /* --- VENDA --- */
    // insert                       (int)                       [ConexaoVenda]
        // getNextID                (int)                       [SubConexaoVenda]
        // insertDindinsVendidos    (int)                       [SubConexaoVenda]
    // updateEstado                 (int)                       [ConexaoVenda]
    // select                       (ArrayList<Venda>)          [ConexaoVenda]
        // selectDistinctIdVenda    (ArrayList<int>)            [SubConexaoVenda]
        // selectDindinsVendidos    (ArrayList<DindinVendido>)  [SubConexaoVenda]
            // select               (Dindin)                    [ConexaoDindin]
    
    /* insert, update, delete e select's (individual e lista) */
    private class ConexaoDindin extends ConexaoBD {
        public int insert(Dindin d){
            int mensagem = conectar();
            
            if (mensagem < 0) {
                return mensagem;
            }
            
            try {
                instrucao = con.prepareStatement("insert into dindin values (?,?,?,?)");

                instrucao.setString(1, d.getSabor());
                instrucao.setDouble(2, d.getCusto());
                instrucao.setDouble(3, d.getValor());
                instrucao.setInt(4, d.getQuantidadeEstoque());

                instrucao.execute();

            } catch (SQLException ex) {
                mensagem = AcessoBD.ERRO_SINTAXE;
            } finally {
                fecharConexao();
            }
            
            return mensagem;
        }

        public int update(Dindin d){
            int mensagem = conectar();
            
            if (mensagem < 0) {
                return mensagem;
            }
            
            try {
                instrucao = con.prepareStatement("update dindin set custo = ?, valor = ?, quantidadeEstoque = ? where sabor = ? limit 1");

                instrucao.setDouble(1, d.getCusto());
                instrucao.setDouble(2, d.getValor());
                instrucao.setInt(3, d.getQuantidadeEstoque());
                instrucao.setString(4, d.getSabor());

                instrucao.execute();

            } catch (SQLException ex) {
                mensagem = AcessoBD.ERRO_SINTAXE;
            } finally {
                fecharConexao();
            }
            
            return mensagem;
        }

        public int delete(Dindin d){
            int mensagem = conectar();
            
            if (mensagem < 0) {
                return mensagem;
            }

            
            try {
                instrucao = con.prepareStatement("delete from dindin where sabor = ? limit 1");

                instrucao.setString(1, d.getSabor());

                instrucao.execute();

            } catch (SQLException ex) {

                if (ex.getMessage().contains("foreign key")) {
                    mensagem = AcessoBD.ERRO_FOREIGN_KEY;
                } else {
                    mensagem = AcessoBD.ERRO_SINTAXE;
                }

            } finally {
                fecharConexao();
            }
            
            return mensagem;
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
                d = null;
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
                lista = null;
            } finally {
                fecharConexao();
            }

            return lista;
        }
    }
    
    /* insert, updateEstado, select */
    private class ConexaoVenda extends ConexaoBD {
        
    }
    
    /* getNextID, selectDistinctIdVenda, insertDindinVendido e selectDindinVendido */
    private class subConexaoVenda extends ConexaoBD {
        
        // consulta os ID's no banco e retorna o próximo disponível 
        private int getNextID() {
            int mensagem = conectar();
            
            if (mensagem < 0) {
                return mensagem;
            }
            
            int id = -1;
            
            try {
                instrucao = con.prepareStatement("select max(idVenda) as id from venda limit 1");
                ResultSet resul = instrucao.executeQuery();

                while (resul.next()) {
                    id = (1 + resul.getInt("id"));
                }

            } catch (SQLException ex) {
                id = ERRO_SINTAXE;
            } finally {
                fecharConexao();
            }

            return id;
        }
        
        // Retorna um objeto de Dindin
        public ArrayList<Integer> selectDistinctIdVenda() {
            int mensagem = conectar();
            
            if (mensagem < 0) {
                return null;
            }

            ArrayList<Integer> lista = new ArrayList<>();

            try {
                instrucao = con.prepareStatement("select idVenda from venda");
                ResultSet resul = instrucao.executeQuery();

                while (resul.next()) {                
                    int idVenda = resul.getInt("idVenda");
                    lista.add(idVenda);
                }

            } catch (SQLException ex) {
                lista = null;
            } finally {
                fecharConexao();
            }

            return lista;
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
    }
    
    /* Mensagens de erro */
    public static final int OPERACAO_CONCLUIDA = 0;
    
    public static final int ERRO_CONECTAR = -1;
    public static final int ERRO_FECHAR_CONEXAO = -2;
    public static final int ERRO_DRIVER = -3;
    
    public static final int ERRO_FOREIGN_KEY = -4;
    public static final int ERRO_SINTAXE = -5;
}
