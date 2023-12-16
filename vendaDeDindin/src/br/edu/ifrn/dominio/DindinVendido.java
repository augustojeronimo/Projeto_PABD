package br.edu.ifrn.dominio;

import java.util.Objects;


public class DindinVendido {
    private final Dindin dindin;
    private final int quantidade;

    public DindinVendido(Dindin dindin, int quantidade) {
        this.dindin = dindin;
        this.quantidade = quantidade;
    }

    public Dindin getDindin() {
        return dindin;
    }

    public int getQuantidade() {
        return quantidade;
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
