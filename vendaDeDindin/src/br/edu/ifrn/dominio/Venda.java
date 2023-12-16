package br.edu.ifrn.dominio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Venda {
    private int idVenda;
    private double valorTotal;
    private double desconto;
    private Date dataVenda;
    private String estado;
    private List<DindinVendido> dindinsVendidos;

    public Venda() {
        dindinsVendidos = new ArrayList<>();
        this.valorTotal = 0;
    }

    public Venda(int idVenda) {
        this.idVenda = idVenda;
        dindinsVendidos = new ArrayList<>();
        this.valorTotal = 0;
    }

    public Venda(int idVenda, double valorTotal) {
        this.idVenda = idVenda;
        this.valorTotal = valorTotal;
        dindinsVendidos = new ArrayList<>();
        this.valorTotal = 0;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Venda other = (Venda) obj;
        if (this.idVenda != other.idVenda) {
            return false;
        }
        if (Double.doubleToLongBits(this.valorTotal) != Double.doubleToLongBits(other.valorTotal)) {
            return false;
        }
        if (Double.doubleToLongBits(this.desconto) != Double.doubleToLongBits(other.desconto)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        if (!Objects.equals(this.dataVenda, other.dataVenda)) {
            return false;
        }
        if (!Objects.equals(this.dindinsVendidos, other.dindinsVendidos)) {
            return false;
        }
        return true;
    }
    
    
    
    
    public void addDindinVendido(DindinVendido dindinVendido){
        if (dindinsVendidos.add(dindinVendido)) {
            valorTotal += dindinVendido.getQuantidade() * dindinVendido.getDindin().getValor();
        }
    }
    
    public void removerDindinVendido(DindinVendido dindinVendido){
        if (dindinsVendidos.remove(dindinVendido)) {
            valorTotal -= dindinVendido.getQuantidade() * dindinVendido.getDindin().getValor();
        }
    }
    
}
