package br.com.dbc.vemser.financeiro.service;

import br.com.dbc.vemser.financeiro.dto.*;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.model.Cartao;
import br.com.dbc.vemser.financeiro.model.Compra;
import br.com.dbc.vemser.financeiro.model.Item;
import br.com.dbc.vemser.financeiro.repository.CompraRepository;
import br.com.dbc.vemser.financeiro.repository.ItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CompraService extends Servico {
    private final CompraRepository compraRepository;
    private final ItemService itemService;
    private final ContaService contaService;
    private final CartaoService cartaoService;

    public CompraService(CompraRepository compraRepository, ObjectMapper objectMapper, ContaService contaService, CartaoService cartaoService, @Lazy ItemService itemService) {
        super(objectMapper);
        this.compraRepository = compraRepository;
        this.contaService = contaService;
        this.cartaoService = cartaoService;
        this.itemService = itemService;
    }
    public List<Compra> list(String login, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        if (login.equals("admin") && senha.equals("abacaxi")) {
            return compraRepository.listar();
        } else {
            throw new RegraDeNegocioException("Credenciais de Administrador inválidas!");
        }
    }

    public List<CompraDTO> retornarComprasCartao(Long numeroCartao, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        contaService.validandoAcessoConta(numeroConta, senha);

        List<CartaoDTO> cartoes = cartaoService.listarPorNumeroConta(numeroConta).stream()
                .filter(cartaoDTO -> cartaoDTO.getNumeroCartao().equals(numeroCartao))
                .toList();
        if(cartoes.size() == 0){
            throw new RegraDeNegocioException("Cartão não existente na conta informada!");
        }else{
            List<CompraDTO> comprasDTO =  compraRepository.listarPorCartao(numeroCartao).stream()
                    .map(compra -> objectMapper.convertValue(compra, CompraDTO.class))
                    .collect(Collectors.toList());

            for (CompraDTO compraDTO : comprasDTO) {
                compraDTO.setItens(itemService.listarItensPorIdCompra(compraDTO.getIdCompra()));
            }

            return comprasDTO;
        }
    }

    public CompraDTO adicionar(CompraCreateDTO compraCreateDTO, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException{
        contaService.validandoAcessoConta(numeroConta, senha);

        Double valorTotal = compraCreateDTO.getItens().stream()
                .mapToDouble(ItemCreateDTO::getValor).sum();

        CartaoDTO cartao = new CartaoDTO();
        cartao.setNumeroCartao(compraCreateDTO.getNumeroCartao());
        cartao.setCodigoSeguranca(compraCreateDTO.getCodigoSeguranca());

        cartaoService.pagar(cartao, valorTotal, numeroConta, senha);

        Compra compra = compraRepository.adicionar(objectMapper.convertValue(compraCreateDTO, Compra.class));
        CompraDTO compraDTO = objectMapper.convertValue(compra, CompraDTO.class);

        for (ItemCreateDTO item : compraCreateDTO.getItens()) {
            item.setIdCompra(compra.getIdCompra());
        }
        List<ItemDTO> listItemDTO = itemService.adicionar(compraCreateDTO.getItens(), numeroConta, senha);

        compraDTO.setItens(listItemDTO);

        return compraDTO;
    }
}
