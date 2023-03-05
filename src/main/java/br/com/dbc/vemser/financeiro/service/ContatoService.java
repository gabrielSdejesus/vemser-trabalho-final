package br.com.dbc.vemser.financeiro.service;

import br.com.dbc.vemser.financeiro.dto.ContatoCreateDTO;
import br.com.dbc.vemser.financeiro.dto.ContatoDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.model.Contato;
import br.com.dbc.vemser.financeiro.repository.ContatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContatoService extends Servico {

    private final ContatoRepository contatoRepository;

    public ContatoService(ContatoRepository contatoRepository, ObjectMapper objectMapper) {
        super(objectMapper);
        this.contatoRepository = contatoRepository;
    }

    public List<ContatoDTO> listarPorIdCliente(Integer idCliente) throws BancoDeDadosException, RegraDeNegocioException {
        return this.contatoRepository.listarContatosPorPessoa(idCliente).stream()
                .map(contato -> objectMapper.convertValue(contato, ContatoDTO.class))
                .collect(Collectors.toList());
    }

    public ContatoDTO retornarContato(Integer idContato) throws BancoDeDadosException, RegraDeNegocioException {
        return objectMapper.convertValue(this.contatoRepository.retornarContato(idContato), ContatoDTO.class);
    }

    public ContatoDTO editar(Integer idContato, ContatoCreateDTO contatoCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        Contato contato = objectMapper.convertValue(contatoCreateDTO, Contato.class);
        return objectMapper.convertValue(this.contatoRepository.editar(idContato, contato), ContatoDTO.class);
    }

    public boolean deletarContato(Integer idContato) throws BancoDeDadosException, RegraDeNegocioException {
        return this.contatoRepository.remover(idContato);
    }

    public ContatoDTO adicionarContato(ContatoCreateDTO contatoCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        Contato contato = objectMapper.convertValue(contatoCreateDTO, Contato.class);
        return objectMapper.convertValue(this.contatoRepository.adicionar(contato), ContatoDTO.class);
    }
}
