package br.com.dbc.vemser.financeiro.service;


import br.com.dbc.vemser.financeiro.dto.EnderecoCreateDTO;
import br.com.dbc.vemser.financeiro.dto.EnderecoDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.model.Endereco;
import br.com.dbc.vemser.financeiro.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnderecoService extends Servico {

    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository, ObjectMapper objectMapper) {
        super(objectMapper);
        this.enderecoRepository = enderecoRepository;
    }

    public EnderecoDTO adicionarEndereco(EnderecoCreateDTO enderecoCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        Endereco endereco = objectMapper.convertValue(enderecoCreateDTO, Endereco.class);
        return objectMapper.convertValue(this.enderecoRepository.adicionar(endereco), EnderecoDTO.class);
    }

    public boolean deletarEndereco(Integer idEndereco) throws BancoDeDadosException, RegraDeNegocioException {
        return this.enderecoRepository.remover(idEndereco);
    }

    public EnderecoDTO alterarEndereco(Integer idEndereco, EnderecoCreateDTO enderecoCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        Endereco endereco = objectMapper.convertValue(enderecoCreateDTO, Endereco.class);
        return objectMapper.convertValue(this.enderecoRepository.editar(idEndereco, endereco), EnderecoDTO.class);
    }

    public EnderecoDTO retornarEndereco(Integer idEndereco) throws BancoDeDadosException, RegraDeNegocioException {
        return objectMapper.convertValue(this.enderecoRepository.retornarEndereco(idEndereco), EnderecoDTO.class);
    }

    public List<EnderecoDTO> retornarEnderecosDoCliente(Integer idCliente) throws BancoDeDadosException, RegraDeNegocioException {
        return this.enderecoRepository.listarEnderecosPorPessoa(idCliente).stream()
                .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
                .collect(Collectors.toList());
    }
}
