package br.com.dbc.vemser.financeiro.service;

import br.com.dbc.vemser.financeiro.dto.CompraCreateDTO;
import br.com.dbc.vemser.financeiro.dto.CompraDTO;
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

    public CompraService(CompraRepository compraRepository, ObjectMapper objectMapper) {
        super(objectMapper);
        this.compraRepository = compraRepository;
    }

    public List<CompraDTO> retornarComprasCartao(Cartao cartao) throws BancoDeDadosException, RegraDeNegocioException {
        return this.compraRepository.listarPorCartao(cartao.getNumeroCartao()).stream()
                .map(compra -> objectMapper.convertValue(compra, CompraDTO.class))
                .collect(Collectors.toList());
    }

    public CompraDTO adicionarCompra(CompraCreateDTO compraCreateDTO) throws BancoDeDadosException, RegraDeNegocioException{
        Compra compra = this.compraRepository.adicionar(objectMapper.convertValue(compraCreateDTO, Compra.class));
        return objectMapper.convertValue(compra, CompraDTO.class);
    }
}
