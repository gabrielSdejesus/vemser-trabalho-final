package br.com.dbc.vemser.financeiro.service;

import br.com.dbc.vemser.financeiro.dto.ItemCreateDTO;
import br.com.dbc.vemser.financeiro.dto.ItemDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.model.Item;
import br.com.dbc.vemser.financeiro.repository.ItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService extends Servico {

    private final ItemRepository itemRepository;
    private final ContaService contaService;

    public ItemService(ItemRepository itemRepository, ObjectMapper objectMapper, ContaService contaService) {
        super(objectMapper);
        this.itemRepository = itemRepository;
        this.contaService = contaService;
    }

    public List<ItemDTO> adicionar(List<ItemCreateDTO> itensCreateDTO, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        contaService.validandoAcessoConta(numeroConta, senha);
        List<Item> itens = itensCreateDTO.stream()
                .map(itemCreateDTO -> objectMapper.convertValue(itemCreateDTO, Item.class))
                .toList();
        List<ItemDTO> itensDTO = new ArrayList<>();
        for(Item item : itens) {
            Item itemCreated = itemRepository.adicionar(item);
            itensDTO.add(objectMapper.convertValue(itemCreated, ItemDTO.class));
        }
        return itensDTO;
    }

    public ItemDTO atualizar(Integer idItem, ItemCreateDTO itemCreateDTO) throws BancoDeDadosException {
        itemRepository.retornarItem(idItem);
        Item item = objectMapper.convertValue(itemCreateDTO, Item.class);
        item.setIdItem(idItem);
        Item itemUpdated = itemRepository.atualizar(item);
        return objectMapper.convertValue(itemUpdated, ItemDTO.class);
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

    public boolean deletar(Integer idItem, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        this.contaService.validandoAcessoConta(numeroConta, senha);
        return itemRepository.deletar(idItem);
    }
}
