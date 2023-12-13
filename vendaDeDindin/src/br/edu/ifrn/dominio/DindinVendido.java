package br.edu.ifrn.dominio;


class DindinVendido {
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
    public String toString() {
        return "DindinVendido{" + "dindin=" + dindin + ", quantidade=" + quantidade + '}';
    }
    
}
