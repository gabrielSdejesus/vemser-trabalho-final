package entities;

public class Item {
    private String nomeItem;
    private double valor, quantidade;

    public Item(String nomeItem, double valor, double quantidade) {
        this.nomeItem = nomeItem;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    public double returnPrecoItem(){
        return valor*quantidade;
    }

}
