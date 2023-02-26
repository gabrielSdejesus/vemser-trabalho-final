package entities.service;

import entities.exception.BancoDeDadosException;
import entities.model.Item;
import entities.repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;

public class ItemService extends Service {

    private ItemRepository itemRepository;

    public ItemService() { this.itemRepository = new ItemRepository();}

    public void adicionar(ArrayList<Item> itens) {
        for (Item item : itens) {
            try {
                itemRepository.adicionar(item);
            } catch (BancoDeDadosException e) {
                e.printStackTrace();
            }
        }
    }

    public void listar() {
        try {
            List<Item> itens = itemRepository.listar();
            for(Item item : itens) {
                System.out.println(item);
            }
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void listarItensPorIdCompra(Integer idCompra) {
        try {
            List<Item> itens = itemRepository.listarItensPorIdCompra(idCompra);
            for(Item item : itens) {
                System.out.println(item);
            }
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

}
