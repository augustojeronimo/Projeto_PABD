package br.edu.ifrn.dominio;

import java.util.Objects;


public class DindinVendido {
    private int idVenda;
    private Dindin dindin;
    private int quantidade;

    public DindinVendido(Dindin dindin, int quantidade) {
        this.dindin = dindin;
        this.quantidade = quantidade;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public Dindin getDindin() {
        return dindin;
    }

    public void setDindin(Dindin dindin) {
        this.dindin = dindin;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
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
        final DindinVendido other = (DindinVendido) obj;
        if (this.quantidade != other.quantidade) {
            return false;
        }
        if (!Objects.equals(this.dindin, other.dindin)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DindinVendido{" + "dindin=" + dindin + ", quantidade=" + quantidade + '}';
    }
    
}
