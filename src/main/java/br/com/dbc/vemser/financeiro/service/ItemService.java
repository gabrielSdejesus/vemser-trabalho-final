package br.com.dbc.vemser.financeiro.service;

import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.model.Item;
import br.com.dbc.vemser.financeiro.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService extends Servico {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) { this.itemRepository = itemRepository;}

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
