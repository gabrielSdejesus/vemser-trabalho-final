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
    private final ClienteService clienteService;

    public ContatoService(ContatoRepository contatoRepository, ClienteService clienteService,
                          ObjectMapper objectMapper) {
        super(objectMapper);
        this.clienteService = clienteService;
        this.contatoRepository = contatoRepository;
    }

    public List<ContatoDTO> listarContatos() throws BancoDeDadosException {
        return contatoRepository.listar().stream()
                .map(contato -> objectMapper.convertValue(contato, ContatoDTO.class))
                .collect(Collectors.toList());
    }

    public List<ContatoDTO> listarContatosDoCliente(Integer idCliente) throws BancoDeDadosException, RegraDeNegocioException {
        //Validando cliente
        clienteService.visualizarCliente(idCliente);
        return contatoRepository.listarContatosPorPessoa(idCliente).stream()
                .map(contato -> objectMapper.convertValue(contato, ContatoDTO.class))
                .collect(Collectors.toList());
    }

    public ContatoDTO retornarContato(Integer idContato) throws BancoDeDadosException, RegraDeNegocioException {
        validarContato(idContato);
        return objectMapper.convertValue(contatoRepository.retornarContato(idContato), ContatoDTO.class);
    }

    public ContatoDTO adicionar(ContatoCreateDTO contatoCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        //Validando cliente
        clienteService.visualizarCliente(contatoCreateDTO.getIdCliente());
        //Validando se o cliente já possui aquele telefone registrado
        validarNumeroContato(contatoCreateDTO);
        Contato contato = objectMapper.convertValue(contatoCreateDTO, Contato.class);
        return objectMapper.convertValue(this.contatoRepository.adicionar(contato), ContatoDTO.class);
    }

    public ContatoDTO atualizar(Integer idContato, ContatoCreateDTO contatoCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        validarContato(idContato);
        validarNumeroContato(contatoCreateDTO);
        Contato contato = objectMapper.convertValue(contatoCreateDTO, Contato.class);
        return objectMapper.convertValue(this.contatoRepository.editar(idContato, contato), ContatoDTO.class);
    }

    public boolean deletar(Integer idContato) throws BancoDeDadosException, RegraDeNegocioException {

        //Validando se o cliente deve ter ao menos 1 contato
        List<ContatoDTO> contatoDTOS = listarContatosDoCliente(retornarContato(idContato).getIdCliente());
        if(contatoDTOS.size() == 1){
            throw new RegraDeNegocioException("É necessário ter ao menos um contato!");
        }
        return this.contatoRepository.remover(idContato);
    }

    private void validarContato(Integer idContato) throws BancoDeDadosException, RegraDeNegocioException {

        if(listarContatos().stream().noneMatch(contato -> contato.getIdContato().equals(idContato))){
            throw new RegraDeNegocioException("Contato não encontrado!");
        }
    }

    private void validarNumeroContato(ContatoCreateDTO contatoCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        if(listarContatos().stream()
                .filter(contatoDTO -> contatoDTO.getIdCliente().equals(contatoCreateDTO.getIdCliente()))
                .anyMatch(contatoDTO -> contatoDTO.getTelefone().equals(contatoCreateDTO.getTelefone()))){
            throw new RegraDeNegocioException("Este número de telefone já existe!");
        }
    }
}
