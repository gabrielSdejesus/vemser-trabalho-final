package entities.model;

import java.util.ArrayList;
import java.util.Date;

public class Compra {
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

}
