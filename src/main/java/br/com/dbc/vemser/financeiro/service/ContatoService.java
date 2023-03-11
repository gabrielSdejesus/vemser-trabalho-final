package br.com.dbc.vemser.financeiro.service;

import br.com.dbc.vemser.financeiro.dto.ContatoCreateDTO;
import br.com.dbc.vemser.financeiro.dto.ContatoDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.model.Contato;
import br.com.dbc.vemser.financeiro.repository.ContatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ContatoService extends Servico {

    private final ContatoRepository contatoRepository;
    private final ClienteService clienteService;
    private final ContaService contaService;

    public ContatoService(ContatoRepository contatoRepository, ClienteService clienteService, ObjectMapper objectMapper, @Lazy ContaService contaService) {
        super(objectMapper);
        this.clienteService = clienteService;
        this.contatoRepository = contatoRepository;
        this.contaService = contaService;
    }

    public List<ContatoDTO> listarContatos(String login, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        if (login.equals("admin") && senha.equals("abacaxi")) {
            return contatoRepository.listar().stream()
                    .map(contato -> objectMapper.convertValue(contato, ContatoDTO.class))
                    .toList();
        }else{
            throw new RegraDeNegocioException("Credenciais de Administrador inválidas!");
        }
    }

    public List<ContatoDTO> listarContatosDoCliente(Integer idCliente) throws BancoDeDadosException, RegraDeNegocioException {
        //Validando cliente
        clienteService.visualizarCliente(idCliente);
        return contatoRepository.listarContatosPorPessoa(idCliente).stream()
                .map(contato -> objectMapper.convertValue(contato, ContatoDTO.class))
                .toList();
    }

    public ContatoDTO retornarContato(Integer idContato, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        contaService.validandoAcessoConta(numeroConta, senha);
        validarContato(idContato);
        if(Objects.equals(contatoRepository.retornarContato(idContato).getIdCliente(), contaService.retornarContaCliente(numeroConta, senha).getCliente().getIdCliente())){
            return objectMapper.convertValue(contatoRepository.retornarContato(idContato), ContatoDTO.class);
        }else{
            throw new RegraDeNegocioException("Esse contato não te pertence!");
        }
    }

    public ContatoDTO adicionar(ContatoCreateDTO contatoCreateDTO, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        //Validando cliente
        contaService.validandoAcessoConta(numeroConta, senha);
        clienteService.visualizarCliente(contatoCreateDTO.getIdCliente());
        //Validando se o cliente já possui aquele telefone registrado
        validarNumeroContato(contatoCreateDTO);
        Contato contato = objectMapper.convertValue(contatoCreateDTO, Contato.class);
        return objectMapper.convertValue(this.contatoRepository.adicionar(contato), ContatoDTO.class);
    }

    public ContatoDTO atualizar(Integer idContato, ContatoCreateDTO contatoCreateDTO, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        contaService.validandoAcessoConta(numeroConta, senha);
        validarContato(idContato);
        validarNumeroContato(contatoCreateDTO);
        Contato contato = objectMapper.convertValue(contatoCreateDTO, Contato.class);
        return objectMapper.convertValue(this.contatoRepository.editar(idContato, contato), ContatoDTO.class);
    }

    public boolean deletar(Integer idContato, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        this.contaService.validandoAcessoConta(numeroConta, senha);
        //Validando se o cliente deve ter ao menos 1 contato
        List<ContatoDTO> contatoDTOS = listarContatosDoCliente(retornarContato(idContato, numeroConta, senha).getIdCliente());
        if(contatoDTOS.size() == 1){
            throw new RegraDeNegocioException("É necessário ter ao menos um contato!");
        }
        return this.contatoRepository.remover(idContato);
    }

    private void validarContato(Integer idContato) throws BancoDeDadosException, RegraDeNegocioException {
        if(contatoRepository.listar().stream().noneMatch(contato -> contato.getIdContato().equals(idContato))){
            throw new RegraDeNegocioException("Contato não encontrado!");
        }
    }

    private void validarNumeroContato(ContatoCreateDTO contatoCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        if(listarContatosDoCliente(contatoCreateDTO.getIdCliente()).stream()
                .anyMatch(contatoDTO -> contatoDTO.getTelefone().equals(contatoCreateDTO.getTelefone()))){
            throw new RegraDeNegocioException("Este número de telefone já existe!");
        }
    }
}
