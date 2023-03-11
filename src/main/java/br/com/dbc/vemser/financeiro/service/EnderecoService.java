package br.com.dbc.vemser.financeiro.service;


import br.com.dbc.vemser.financeiro.dto.ClienteDTO;
import br.com.dbc.vemser.financeiro.dto.ContaDTO;
import br.com.dbc.vemser.financeiro.dto.EnderecoCreateDTO;
import br.com.dbc.vemser.financeiro.dto.EnderecoDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.model.Cliente;
import br.com.dbc.vemser.financeiro.model.Endereco;
import br.com.dbc.vemser.financeiro.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EnderecoService extends Servico {

    private final EnderecoRepository enderecoRepository;
    private final ClienteService clienteService;
    private final ContaService contaService;

    public EnderecoService(EnderecoRepository enderecoRepository, ClienteService clienteService, ObjectMapper objectMapper, ContaService contaService) {
        super(objectMapper);
        this.enderecoRepository = enderecoRepository;
        this.clienteService = clienteService;
        this.contaService = contaService;
    }

    public List<EnderecoDTO> listarEnderecos(String login, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        if (login.equals("admin") && senha.equals("abacaxi")) {
            return enderecoRepository.listar().stream()
                    .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
                    .toList();
        }else{
            throw new RegraDeNegocioException("Credenciais de Administrador inválidas!");
        }
    }

    public List<EnderecoDTO> listarEnderecosDoCliente(Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        ContaDTO conta = contaService.validandoAcessoConta(numeroConta, senha);
        clienteService.visualizarCliente(conta.getCliente().getIdCliente());
        return this.enderecoRepository.listarEnderecosPorPessoa(conta.getCliente().getIdCliente()).stream()
                .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
                .collect(Collectors.toList());
    }

    public EnderecoDTO retornarEndereco(Integer idEndereco, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        contaService.validandoAcessoConta(numeroConta, senha);
        validarEndereco(idEndereco);
        if(Objects.equals(this.enderecoRepository.retornarEndereco(idEndereco).getIdCliente(), contaService.retornarContaCliente(numeroConta, senha).getCliente().getIdCliente())){
            return objectMapper.convertValue(this.enderecoRepository.retornarEndereco(idEndereco), EnderecoDTO.class);
        }else{
            throw new RegraDeNegocioException("Esse endereço não te pertence!");
        }
    }

    public EnderecoDTO adicionar(EnderecoCreateDTO enderecoCreateDTO, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        this.contaService.validandoAcessoConta(numeroConta, senha);
        //Validando cliente
        clienteService.visualizarCliente(enderecoCreateDTO.getIdCliente());
        //Validando se o cliente já possui aquele cep registrado no banco de dados.
        validarCEPEndereco(enderecoCreateDTO);
        Endereco endereco = objectMapper.convertValue(enderecoCreateDTO, Endereco.class);
        return objectMapper.convertValue(this.enderecoRepository.adicionar(endereco), EnderecoDTO.class);
    }

    public EnderecoDTO atualizar(Integer idEndereco, EnderecoCreateDTO enderecoCreateDTO, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        contaService.validandoAcessoConta(numeroConta, senha);
        validarEndereco(idEndereco);
        validarCEPEndereco(enderecoCreateDTO);
        Endereco endereco = objectMapper.convertValue(enderecoCreateDTO, Endereco.class);
        return objectMapper.convertValue(enderecoRepository.editar(idEndereco, endereco), EnderecoDTO.class);
    }

    public boolean deletar(Integer idEndereco, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        List<EnderecoDTO> enderecoDTOS = listarEnderecosDoCliente(numeroConta, senha);

        if(enderecoDTOS.size() == 1){
            throw new RegraDeNegocioException("É necessário ter ao menos um endereço!");
        }
        return this.enderecoRepository.remover(idEndereco);
    }

    private void validarEndereco(Integer idEndereco) throws BancoDeDadosException, RegraDeNegocioException {
        if(enderecoRepository.listar().stream().noneMatch(endereco -> endereco.getIdEndereco().equals(idEndereco))){
            throw new RegraDeNegocioException("Endereço não encontrado!");
        }
    }

    private void validarCEPEndereco(EnderecoCreateDTO enderecoCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        if(enderecoRepository.listar().stream()
                .filter(enderecoDTO -> enderecoDTO.getIdCliente().equals(enderecoCreateDTO.getIdCliente()))
                .anyMatch(enderecoDTO -> enderecoDTO.getCep().equals(enderecoCreateDTO.getCep()))){
            throw new RegraDeNegocioException("Este CEP já existe!");
        }
    }
}