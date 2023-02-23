package entities.model;

import entities.interfaces.Exibicao;

public class Item {

    private Integer idItem;
    private String nome;
    private Double valor;

    public Item() {
    }

    public Item(Integer idItem, String nome, Double valor) {
        this.idItem = idItem;
        this.nome = nome;
        this.valor = valor;
    }

    public Integer getIdItem() {
        return idItem;
    }

    public void setIdItem(Integer idItem) {
        this.idItem = idItem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Item{" +
                "idItem=" + idItem +
                ", nome='" + nome + '\'' +
                ", valor=" + valor +
                '}';
    }
}
