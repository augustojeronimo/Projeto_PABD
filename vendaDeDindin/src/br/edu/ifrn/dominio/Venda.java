package br.edu.ifrn.dominio;

import java.util.ArrayList;
import java.sql.Date;
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
        this.estado = "operante";
    }

    public Venda(int idVenda) {
        this.idVenda = idVenda;
        dindinsVendidos = new ArrayList<>();
        this.valorTotal = 0;
        this.estado = "operante";
    }
    
    
    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
        for (DindinVendido dv : dindinsVendidos) {
            dv.setIdVenda(idVenda);
        }
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<DindinVendido> getDindinsVendidos() {
        return dindinsVendidos;
    }

    public void setDindinsVendidos(List<DindinVendido> dindinsVendidos) {
        this.dindinsVendidos = dindinsVendidos;
        
        for (DindinVendido dv : dindinsVendidos) {
            this.valorTotal += dv.getQuantidade() * dv.getDindin().getValor();
        }
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
    
    public String getSaboresVendidosToString() {
        String str_dindins = "";
        
        int tam = dindinsVendidos.size();
        
        for (int i = 0; i < tam; i++) {
            String sabor = dindinsVendidos.get(i).getDindin().getSabor();
            int quantidade = dindinsVendidos.get(i).getQuantidade();
            
            str_dindins += (i == 0? "":", ") + sabor + " ["+quantidade+"]";
        }
        
        return str_dindins;
    }
    
    public void addDindinVendido(DindinVendido dindinVendido){
        if (dindinsVendidos.add(dindinVendido)) {
            dindinVendido.setIdVenda(this.idVenda);
            valorTotal += dindinVendido.getQuantidade() * dindinVendido.getDindin().getValor();
        }
    }
    
    public void removerDindinVendido(DindinVendido dindinVendido){
        if (dindinsVendidos.remove(dindinVendido)) {
            valorTotal -= dindinVendido.getQuantidade() * dindinVendido.getDindin().getValor();
        }
    }
    
}
