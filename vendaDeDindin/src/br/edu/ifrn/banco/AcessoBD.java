package br.edu.ifrn.banco;

import br.edu.ifrn.dominio.Dindin;
import br.edu.ifrn.dominio.DindinVendido;
import br.edu.ifrn.dominio.Venda;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class AcessoBD {
    /* --- DINDIN --- */

    public int InsertDindin(Dindin d) {
        return new ConexaoDindin().insert(d);
    }

    public int updateDindin(Dindin d) {
        return new ConexaoDindin().update(d);
    }

    public int deleteDindin(Dindin d) {
        return new ConexaoDindin().delete(d);
    }

    public Dindin selectDindin(String sabor) {
        return new ConexaoDindin().selectDindin(sabor);
    }

    public ArrayList<Dindin> selectDindins() {
        return new ConexaoDindin().selectDindins();
    }
    
    
    
    /* --- VENDA --- */

    public int insertVenda(Venda venda) {
        int idVenda = new subConexaoVenda().getNextID();
        
        if (idVenda < 0) return idVenda;
        
        venda.setIdVenda(idVenda);
        
        int mensagem = new ConexaoVenda().insert(venda);
        
        if (mensagem < 0) return mensagem;
        
        for (DindinVendido dv : venda.getDindinsVendidos()) {
            mensagem = new subConexaoVenda().insertDindinVendido(dv);
            
            if (mensagem < 0) return mensagem;
        }
        
        mensagem = new subConexaoVenda().executarBaixaDindins(venda);
        
        return mensagem;
    }
    
    public int alternarEstadoVenda(Venda venda) {
        if (!venda.getEstado().equals(Venda.VENDA_INDEFERIDA) && !venda.getEstado().equals(Venda.VENDA_INDEFERIDA)) {
            return AcessoBD.ERRO_SINTAXE;
        } else {
            return new ConexaoVenda().alternarEstado(venda);
        }
    }
    
    public ArrayList<Venda> selectVendas() {
        ArrayList<Venda> listaVenda = new ArrayList<>();
        
        ArrayList<Integer> listaId = new ConexaoVenda().selectDistinctIdVenda();
        if (listaId == null) return null;
        
        for (Integer id : listaId) {
            Venda venda = new ConexaoVenda().selectVenda(id);
            if (venda == null) return null;
            
            listaVenda.add(venda);
            
            ArrayList<DindinVendido> listaDindinVendido = new subConexaoVenda().selectDindinsVendidos(id);
            if (listaDindinVendido == null) return null;
            
            venda.setDindinsVendidos(listaDindinVendido);
            
            for (DindinVendido dv : listaDindinVendido) {
                Dindin dindin = new ConexaoDindin().selectDindin(dv.getDindin().getSabor());
                if (dindin == null) return null;
                
                dv.setDindin(dindin);
            }
        }
        
        return listaVenda;
        
    }
    


    
    /* insert, update, delete e select's (individual e lista) */
    private class ConexaoDindin extends ConexaoBD {
        
        private int insert(Dindin d){
            int mensagem = conectar();
            
            if (mensagem < 0) {
                fecharConexao();
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

        private int update(Dindin d){
            int mensagem = conectar();
            
            if (mensagem < 0) {
                fecharConexao();
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

        private int delete(Dindin d){
            int mensagem = conectar();
            
            if (mensagem < 0) {
                fecharConexao();
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
        private Dindin selectDindin(String sabor) {
            int mensagem = conectar();
            
            if (mensagem < 0) {
                fecharConexao();
                return null;
            }

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
        private ArrayList<Dindin> selectDindins() {
            int mensagem = conectar();
            
            if (mensagem < 0) {
                fecharConexao();
                return null;
            }

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
    
    /* insert, updateEstado, selectVenda e selectVendas */
    private class ConexaoVenda extends ConexaoBD {
        
        // atualiza o estado da venda no banco de dados
        private int alternarEstado(Venda v) {
            int mensagem = conectar();
            
            if (mensagem < 0) {
                fecharConexao();
                return mensagem;
            }
            
            if (v.getEstado().equals(Venda.VENDA_INDEFERIDA)) {
                v.setEstado(Venda.VENDA_OPERANTE);
            } else {
                v.setEstado(Venda.VENDA_INDEFERIDA);
            }
            
            try {
                instrucao = con.prepareStatement("update venda set estado = ? where idVenda = ? limit 1");
                
                instrucao.setString(1, v.getEstado());
                instrucao.setInt(2, v.getIdVenda());
                
                instrucao.execute();
                
            } catch (SQLException ex) {
                mensagem = AcessoBD.ERRO_SINTAXE;
            }
            
            return mensagem;
        }
        
        // insere um objeto Venda no banco de dados
        private int insert(Venda v){
            int mensagem = conectar();
            
            if (mensagem < 0) {
                fecharConexao();
                return mensagem;
            }

            v.setIdVenda(new subConexaoVenda().getNextID());
            
            if (v.getIdVenda() < 0) {
                fecharConexao();
                return v.getIdVenda();
            }

            try {
                instrucao = con.prepareStatement("insert into venda values (?,?,?,?,?)");

                instrucao.setDouble(1, v.getIdVenda());
                instrucao.setDouble(2, v.getValorTotal());
                instrucao.setDouble(3, v.getDesconto());
                instrucao.setDate(4, v.getDataVenda());
                instrucao.setString(5, v.getEstado());

                instrucao.execute();

            } catch (SQLException ex) {
                mensagem = AcessoBD.ERRO_SINTAXE;
            } finally {
                fecharConexao();
            }
            
            return mensagem;
        }
        
        
        // retorna um ArrayList de objetos Venda
        private Venda selectVenda(int idVenda) {
            int mensagem = conectar();
            
            if (mensagem < 0) {
                fecharConexao();
                return null;
            }
            
            Venda v = new Venda(idVenda);

            try {
                
                instrucao = con.prepareStatement("select * from venda where idVenda = ?");

                instrucao.setInt(1, idVenda);

                ResultSet resul = instrucao.executeQuery();

                if (resul.next()) {

                    v.setIdVenda(resul.getInt("idVenda"));
                    v.setValorTotal(resul.getDouble("valorTotal"));
                    v.setDesconto(resul.getDouble("desconto"));
                    v.setDataVenda(resul.getDate("dataVenda"));
                    v.setEstado(resul.getString("estado"));

                }
                    
                
            } catch (SQLException ex) {
                v = null;
            } finally {
                fecharConexao();
            }

            return v;
        }
        
        
        // retorna um ArrayList contendo os ID's das vendas ordenadas por data e número
        private ArrayList<Integer> selectDistinctIdVenda() {
            int mensagem = conectar();
            
            if (mensagem < 0) {
                fecharConexao();
                return null;
            }

            ArrayList<Integer> lista = new ArrayList<>();

            try {
                instrucao = con.prepareStatement("select idVenda from venda order by dataVenda desc, idVenda desc");
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
    }
    
    
    /* getNextID, selectDistinctIdVenda, insertDindinVendido e selectDindinVendido */
    private class subConexaoVenda extends ConexaoBD {
        
        // consulta os ID's no banco e retorna o próximo disponível 
        private int getNextID() {
            int mensagem = conectar();
            
            if (mensagem < 0) {
                fecharConexao();
                return mensagem;
            }
            
            int id = -1;
            
            try {
                instrucao = con.prepareStatement("select max(idVenda) as id from venda limit 1");
                ResultSet resul = instrucao.executeQuery();

                if (resul.next()) {
                    id = (1 + resul.getInt("id"));
                }

            } catch (SQLException ex) {
                id = ERRO_SINTAXE;
            } finally {
                fecharConexao();
            }

            return id;
        }
        
        // insere um objeto DindinVendido no banco de dados
        private int insertDindinVendido(DindinVendido dv){
            int mensagem = conectar();
            
            if (mensagem < 0) {
                fecharConexao();
                return mensagem;
            }
            
            try {
                instrucao = con.prepareStatement("insert into dindinVendido values (?,?,?)");
                
                instrucao.setInt(1, dv.getIdVenda());
                instrucao.setString(2, dv.getDindin().getSabor());
                instrucao.setInt(3, dv.getQuantidade());

                instrucao.execute();
                
            } catch (SQLException ex) {
                mensagem = ERRO_SINTAXE;
            } finally {
                fecharConexao();
            }
            
            return mensagem;
        }
        
        // retorna uma ArrayList com os objetos DindinVendido como idVenda informado
        private ArrayList<DindinVendido> selectDindinsVendidos(int idVenda) {
            int mensagem = conectar();
            
            if (mensagem < 0) {
                fecharConexao();
                return null;
            }

            ArrayList<DindinVendido> lista = new ArrayList<>();

            try {
                instrucao = con.prepareStatement("select * from dindinVendido where idVenda = ?");
                
                instrucao.setInt(1, idVenda);
                
                ResultSet resul = instrucao.executeQuery();
                
                while (resul.next()) {                    
                    DindinVendido dv = new DindinVendido(new Dindin(), 0);
                    
                    dv.setIdVenda(resul.getInt("idVenda"));
                    dv.setDindin(
                            new ConexaoDindin().selectDindin(
                                    resul.getString("saborDindin")
                            )
                    );
                    dv.setQuantidade(resul.getInt("quantidade"));
                    
                    if (dv.getDindin() == null) {
                        fecharConexao();
                        return null;
                    }
                    
                    lista.add(dv);
                }
                
            } catch (SQLException ex) {
                lista = null;
            } finally {
                fecharConexao();
            }

            return lista;
        }
        
        // atualiza a quantidade de dindins de acordo com a venda
        private int executarBaixaDindins(Venda venda) {
            int mensagem = conectar();
            
            if (mensagem < 0) {
                fecharConexao();
                return mensagem;
            }
            
            try {
                
                
                for (DindinVendido dv : venda.getDindinsVendidos()) {
                    instrucao = con.prepareStatement("update dindin set quantidadeEstoque = quantidadeEstoque - ? where sabor = ?");
                    
                    String sabor = dv.getDindin().getSabor();
                    int quantidade = dv.getQuantidade();
                    
                    instrucao.setInt(1, quantidade);
                    instrucao.setString(2, sabor);
                    
                    instrucao.execute();
                    
                }
                
            } catch (SQLException ex) {
                mensagem = AcessoBD.ERRO_SINTAXE;
            }
            
            return mensagem;
        }
        
        // atualiza a quantidade de dindins de acordo com a venda cancelada
        private int executarReposicaoDindins(Venda venda) {
            int mensagem = conectar();
            
            if (mensagem < 0) {
                fecharConexao();
                return mensagem;
            }
            
            try {
                
                
                for (DindinVendido dv : venda.getDindinsVendidos()) {
                    instrucao = con.prepareStatement("update dindin set quantidadeEstoque = ? where sabor = ?");
                    
                    String sabor = dv.getDindin().getSabor();
                    int quantidade = (new ConexaoDindin().selectDindin(sabor).getQuantidadeEstoque())+(dv.getQuantidade());
                    
                    instrucao.setInt(1, quantidade);
                    instrucao.setString(2, sabor);
                    
                    instrucao.execute();
                    
                }
                
            } catch (SQLException ex) {
                mensagem = AcessoBD.ERRO_SINTAXE;
            }
            
            return mensagem;
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
