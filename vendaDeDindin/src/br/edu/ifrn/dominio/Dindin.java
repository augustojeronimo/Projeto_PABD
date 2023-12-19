package br.edu.ifrn.dominio;

import java.util.Objects;

public class Dindin {
    private String sabor;
    private double custo;
    private double valor;
    private int quantidadeEstoque;

    public Dindin() {}

    public Dindin(String sabor) {
        this.sabor = sabor;
    }

    public Dindin(String sabor, double custo, double valor, int quantidadeEstoque) {
        this.sabor = sabor;
        this.custo = custo;
        this.valor = valor;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
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
        final Dindin other = (Dindin) obj;
        if (Double.doubleToLongBits(this.custo) != Double.doubleToLongBits(other.custo)) {
            return false;
        }
        if (Double.doubleToLongBits(this.valor) != Double.doubleToLongBits(other.valor)) {
            return false;
        }
        if (this.quantidadeEstoque != other.quantidadeEstoque) {
            return false;
        }
        if (!Objects.equals(this.sabor, other.sabor)) {
            return false;
        }
        return true;
    }
    
}
