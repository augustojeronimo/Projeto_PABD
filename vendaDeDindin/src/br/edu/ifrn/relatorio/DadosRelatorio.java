/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrn.relatorio;

import java.util.Date;

/**
 *
 * @author devel
 */
public class DadosRelatorio {
    
    private int idVenda;
    private String saboresVendidos;
    private double valorTotal;
    private double desconto;
    private Date data;
    private String estado;

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public String getSaboresVendidos() {
        return saboresVendidos;
    }

    public void setSaboresVendidos(String saboresVendidos) {
        this.saboresVendidos = saboresVendidos;
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
    
}
