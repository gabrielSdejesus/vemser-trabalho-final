package entities.model;

import entities.interfaces.Exibicao;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class Compra implements Exibicao {
    private String docVendedor;
    private Date data;
    private ArrayList<Item> itens;

    public Compra(String docVendedor, Date data, ArrayList<Item> itens) {
        this.docVendedor = docVendedor;
        this.data = data;
        this.itens = itens;
    }

    public double returnValorTotal(){
        double total = 0;
        for(Item item: itens){
            total += item.returnPrecoItem();
        }
        return total;
    }

    @Override
    public void exibir(){
        System.out.println("\n\tDocumento do vendedor: "+docVendedor);
        System.out.println("\tData da compra: "+data);
        System.out.println("\tItens da compra:");
        for(Item item: itens){
            item.exibir();
        }
    }

    public LocalDate getData(){
        return data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
