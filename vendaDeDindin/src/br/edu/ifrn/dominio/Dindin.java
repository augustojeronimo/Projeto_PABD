package br.edu.ifrn.dominio;

public class Dindin {
    private String sabor;
    private double valor;
    private int quantidadeEstoque;

    public Dindin() {}

    public Dindin(String sabor) {
        this.sabor = sabor;
    }

    public Dindin(String sabor, double valor, int quantidadeEstoque) {
        this.sabor = sabor;
        this.valor = valor;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
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
    public String toString() {
        return "Dindin{" + "sabor=" + sabor + ", valor=" + valor + ", quantidadeEstoque=" + quantidadeEstoque + '}';
    }

}
