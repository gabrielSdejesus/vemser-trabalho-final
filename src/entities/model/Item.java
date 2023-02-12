package entities.model;

import entities.interfaces.Exibicao;

public class Item implements Exibicao {
    private String nomeItem;
    private double valor, quantidade;

    public Item() {
    }

    public Item(String nomeItem, double valor, double quantidade) {
        this.nomeItem = nomeItem;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    public double returnPrecoItem(){
        return valor*quantidade;
    }

    @Override
    public void exibir() {
        System.out.printf("\t\tItem: %s; Valor unitário: %.2f; Quantidade: %.2f; Valor total: %.2f\n", nomeItem, valor, quantidade, this.returnPrecoItem());
    }

    public String getNomeItem() {
        return nomeItem;
    }
}
