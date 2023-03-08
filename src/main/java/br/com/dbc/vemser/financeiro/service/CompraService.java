package br.com.dbc.vemser.financeiro.service;

import br.com.dbc.vemser.financeiro.dto.CartaoDTO;
import br.com.dbc.vemser.financeiro.dto.CompraCreateDTO;
import br.com.dbc.vemser.financeiro.dto.CompraDTO;
import br.com.dbc.vemser.financeiro.dto.ContaAcessDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.model.Cartao;
import br.com.dbc.vemser.financeiro.model.Compra;
import br.com.dbc.vemser.financeiro.repository.CompraRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompraService extends Servico {
    private final CompraRepository compraRepository;
    private final ContaService contaService;
    private final CartaoService cartaoService;

    public CompraService(CompraRepository compraRepository, ObjectMapper objectMapper, ContaService contaService, CartaoService cartaoService) {
        super(objectMapper);
        this.compraRepository = compraRepository;
        this.contaService = contaService;
        this.cartaoService = cartaoService;
    }

    public List<CompraDTO> retornarComprasCartao(Long numeroCartao, ContaAcessDTO contaAcessDTO) throws BancoDeDadosException, RegraDeNegocioException {

        contaService.validandoAcessoConta(contaAcessDTO);

        CartaoDTO cartao = cartaoService.listarPorNumeroConta(contaAcessDTO.getNumeroConta()).stream()
                .filter(cartaoDTO -> cartaoDTO.getNumeroCartao().equals(numeroCartao))
                .findFirst()
                .orElseThrow(()-> new RegraDeNegocioException("Cartão não existente na conta informada!"));

        return compraRepository.listarPorCartao(numeroCartao).stream()
                .map(compra -> objectMapper.convertValue(compra, CompraDTO.class))
                .collect(Collectors.toList());
    }

    public CompraDTO adicionarCompra(CompraCreateDTO compraCreateDTO) throws BancoDeDadosException, RegraDeNegocioException{
        Compra compra = this.compraRepository.adicionar(objectMapper.convertValue(compraCreateDTO, Compra.class));
        return objectMapper.convertValue(compra, CompraDTO.class);
    }
}
