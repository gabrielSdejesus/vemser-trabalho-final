package br.com.dbc.vemser.financeiro.service;


import java.util.List;
import java.util.stream.Collectors;

import br.com.dbc.vemser.financeiro.dto.CartaoDTO;
import br.com.dbc.vemser.financeiro.dto.CartaoCreateDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.model.*;
import br.com.dbc.vemser.financeiro.repository.CartaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CartaoService extends Servico {

    private final CartaoRepository cartaoRepository;

    public CartaoService(CartaoRepository cartaoRepository, ObjectMapper objectMapper) {
        super(objectMapper);
        this.cartaoRepository = cartaoRepository;
    }

    public List<CartaoDTO> listarPorNumeroConta(Integer numeroConta) throws BancoDeDadosException {
        List<Cartao> cartoes = cartaoRepository.listarPorNumeroConta(numeroConta);
        return cartoes.stream()
                .map(cartao -> objectMapper.convertValue(cartao, CartaoDTO.class))
                .collect(Collectors.toList());
    }

    public CartaoDTO criar(Integer numeroConta, CartaoCreateDTO cartaoCreateDTO) throws Exception {
        List<Cartao> cartoes = cartaoRepository.listarPorNumeroConta(numeroConta);
        if (cartoes.size() == 2) {
            throw new RegraDeNegocioException("Usuário já possui dois cartões");
        } else {
            Cartao cartao = null;
            if (cartaoCreateDTO.getTipo().equals("DEBITO")) {
                cartao = objectMapper.convertValue(cartaoCreateDTO, CartaoDeDebito.class);
            } else {
                cartao = objectMapper.convertValue(cartaoCreateDTO, CartaoDeCredito.class);
            }
            cartao.setNumeroConta(numeroConta);
            Cartao cartaoCriado = cartaoRepository.adicionar(cartao);
            return objectMapper.convertValue(cartaoCriado, CartaoDTO.class);
        }
    }

    public CartaoDTO atualizar(Long numeroCartao, CartaoCreateDTO cartaoCreateDTO) throws RegraDeNegocioException, BancoDeDadosException {
        Cartao cartao = cartaoRepository.getPorNumeroCartao(numeroCartao);
        Cartao cartaoEditado = null;
        if(cartao.getTipo().equals("DEBITO")) {
            cartaoEditado = cartaoRepository.editar(numeroCartao, objectMapper.convertValue(cartaoCreateDTO, CartaoDeDebito.class));
        } else {
            cartaoEditado = cartaoRepository.editar(numeroCartao, objectMapper.convertValue(cartaoCreateDTO, CartaoDeCredito.class));
        }
        return objectMapper.convertValue(cartaoEditado, CartaoDTO.class);
    }

    public void deletar(Long numeroCartao) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Buscando cartão...");
        Cartao cartao = cartaoRepository.getPorNumeroCartao(numeroCartao);
        List<Cartao> cartoes = cartaoRepository.listarPorNumeroConta(cartao.getNumeroConta());
        if (cartoes.size() == 1) {
            throw new RegraDeNegocioException("Cliente possui apenas um cartão");
        } else {
            cartaoRepository.remover(numeroCartao);
        }
    }

}
