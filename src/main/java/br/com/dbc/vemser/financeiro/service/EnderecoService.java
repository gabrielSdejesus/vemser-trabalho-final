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
    private final ClienteService clienteService;

    public EnderecoService(EnderecoRepository enderecoRepository, ClienteService clienteService, ObjectMapper objectMapper) {
        super(objectMapper);
        this.enderecoRepository = enderecoRepository;
        this.clienteService = clienteService;
    }

    public List<EnderecoDTO> listarEnderecos() throws BancoDeDadosException {
        return enderecoRepository.listar().stream()
                .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
                .collect(Collectors.toList());
    }

    public List<EnderecoDTO> listarEnderecosDoCliente(Integer idCliente) throws BancoDeDadosException, RegraDeNegocioException {
        //Validando cliente
        clienteService.visualizarCliente(idCliente);
        return this.enderecoRepository.listarEnderecosPorPessoa(idCliente).stream()
                .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
                .collect(Collectors.toList());
    }

    public EnderecoDTO retornarEndereco(Integer idEndereco) throws BancoDeDadosException, RegraDeNegocioException {
        validarEndereco(idEndereco);
        return objectMapper.convertValue(this.enderecoRepository.retornarEndereco(idEndereco), EnderecoDTO.class);
    }

    public EnderecoDTO adicionar(EnderecoCreateDTO enderecoCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        //Validando cliente
        clienteService.visualizarCliente(enderecoCreateDTO.getIdCliente());
        //Validando se o cliente já possui aquele cep registrado no banco de dados.
        validarCEPEndereco(enderecoCreateDTO);
        Endereco endereco = objectMapper.convertValue(enderecoCreateDTO, Endereco.class);
        return objectMapper.convertValue(this.enderecoRepository.adicionar(endereco), EnderecoDTO.class);
    }

    public EnderecoDTO atualizar(Integer idEndereco, EnderecoCreateDTO enderecoCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        validarEndereco(idEndereco);
        validarCEPEndereco(enderecoCreateDTO);
        Endereco endereco = objectMapper.convertValue(enderecoCreateDTO, Endereco.class);
        return objectMapper.convertValue(enderecoRepository.editar(idEndereco, endereco), EnderecoDTO.class);
    }

    public boolean deletar(Integer idEndereco) throws BancoDeDadosException, RegraDeNegocioException {

        //Validando se o cliente deve ter ao menos 1 endereço
        List<EnderecoDTO> enderecoDTOS = listarEnderecosDoCliente(retornarEndereco(idEndereco).getIdCliente());
        if(enderecoDTOS.size() == 1){
            throw new RegraDeNegocioException("É necessário ter ao menos um endereço!");
        }
        return this.enderecoRepository.remover(idEndereco);
    }

    private void validarEndereco(Integer idEndereco) throws BancoDeDadosException, RegraDeNegocioException {

        if(listarEnderecos().stream().noneMatch(endereco -> endereco.getIdEndereco().equals(idEndereco))){
            throw new RegraDeNegocioException("Endereço não encontrado!");
        }
    }

    private void validarCEPEndereco(EnderecoCreateDTO enderecoCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        if(listarEnderecos().stream()
                .filter(enderecoDTO -> enderecoDTO.getIdCliente().equals(enderecoCreateDTO.getIdCliente()))
                .anyMatch(enderecoDTO -> enderecoDTO.getCep().equals(enderecoCreateDTO.getCep()))){
            throw new RegraDeNegocioException("Este CEP já existe!");
        }
    }
}
