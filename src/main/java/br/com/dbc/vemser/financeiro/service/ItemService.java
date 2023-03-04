package br.com.dbc.vemser.financeiro.service;

import br.com.dbc.vemser.financeiro.dto.ItemCreateDTO;
import br.com.dbc.vemser.financeiro.dto.ItemDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.model.Item;
import br.com.dbc.vemser.financeiro.repository.ItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService extends Servico {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository, ObjectMapper objectMapper) {
        super(objectMapper);
        this.itemRepository = itemRepository;
    }

    public ItemDTO adicionar(ItemCreateDTO itemCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        Item item = objectMapper.convertValue(itemCreateDTO, Item.class);
        return objectMapper.convertValue(this.itemRepository.adicionar(item), ItemDTO.class);
    }

    public ItemDTO retornarItem(Integer idItem) throws BancoDeDadosException, RegraDeNegocioException {
        return objectMapper.convertValue(this.itemRepository.retornarItem(idItem), ItemDTO.class);
    }

    public List<ItemDTO> listar() throws BancoDeDadosException, RegraDeNegocioException {
        return itemRepository.listar().stream()
                .map(item -> objectMapper.convertValue(item, ItemDTO.class))
                .collect(Collectors.toList());
    }

    public List<ItemDTO> listarItensPorIdCompra(Integer idCompra) throws BancoDeDadosException, RegraDeNegocioException {
        return itemRepository.listarItensPorIdCompra(idCompra).stream()
                .map(item -> objectMapper.convertValue(item, ItemDTO.class))
                .collect(Collectors.toList());
    }
}
