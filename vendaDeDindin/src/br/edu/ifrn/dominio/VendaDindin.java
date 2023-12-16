package br.edu.ifrn.dominio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VendaDindin {
    private int idVenda;
    private double valorTotal;
    private double desconto;
    private Date dataVenda;
    private String estado;
    private List<DindinVendido> dindinsVendidos;

    public VendaDindin() {
        dindinsVendidos = new ArrayList<>();
    }

    public VendaDindin(int idVenda) {
        this.idVenda = idVenda;
        dindinsVendidos = new ArrayList<>();
    }

    public VendaDindin(int idVenda, double valorTotal) {
        this.idVenda = idVenda;
        this.valorTotal = valorTotal;
        dindinsVendidos = new ArrayList<>();
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    public void addDindinVendido(DindinVendido dindinVendido){
        dindinsVendidos.add(dindinVendido);
    }
    
    public void removerDindinVendido(DindinVendido dindinVendido){
        dindinsVendidos.remove(dindinVendido);
    }
    
}
