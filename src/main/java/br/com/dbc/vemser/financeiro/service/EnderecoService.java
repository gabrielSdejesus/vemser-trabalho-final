package br.com.dbc.vemser.financeiro.service;


import br.com.dbc.vemser.financeiro.dto.ClienteDTO;
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

    public List<EnderecoDTO> listarEnderecos() throws BancoDeDadosException, RegraDeNegocioException {
        return enderecoRepository.listar().stream()
                .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
                .collect(Collectors.toList());
    }

    public List<EnderecoDTO> retornarEnderecosDoCliente(Integer idCliente) throws BancoDeDadosException, RegraDeNegocioException {
        validarPessoaEndereco(idCliente);
        return this.enderecoRepository.listarEnderecosPorPessoa(idCliente).stream()
                .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
                .collect(Collectors.toList());
    }

    public EnderecoDTO retornarEndereco(Integer idEndereco) throws BancoDeDadosException, RegraDeNegocioException {
        validarEndereco(idEndereco);
        return objectMapper.convertValue(this.enderecoRepository.retornarEndereco(idEndereco), EnderecoDTO.class);
    }

    public EnderecoDTO adicionarEndereco(EnderecoCreateDTO enderecoCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        validarPessoaEndereco(enderecoCreateDTO.getIdCliente());
        //Validando se o cliente já possui aquele cep registrado no banco de dados.
        if(listarEnderecos().stream()
                .filter(enderecoDTO -> enderecoDTO.getIdCliente().equals(enderecoCreateDTO.getIdCliente()))
                .anyMatch(enderecoDTO -> enderecoDTO.getCep().equals(enderecoCreateDTO.getCep()))){
            throw new RegraDeNegocioException("Não é possível criar o mesmo CEP no mesmo cliente!");
        }
        Endereco endereco = objectMapper.convertValue(enderecoCreateDTO, Endereco.class);
        return objectMapper.convertValue(this.enderecoRepository.adicionar(endereco), EnderecoDTO.class);
    }

    public EnderecoDTO atualizarEndereco(Integer idEndereco, EnderecoCreateDTO enderecoCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        validarEndereco(idEndereco);
        Endereco endereco = objectMapper.convertValue(enderecoCreateDTO, Endereco.class);
        return objectMapper.convertValue(enderecoRepository.editar(idEndereco, endereco), EnderecoDTO.class);
    }

    public boolean deletarEndereco(Integer idEndereco) throws BancoDeDadosException, RegraDeNegocioException {

        //Validando onde o cliente deve ter ao menos 1 endereço
        List<EnderecoDTO> enderecoDTOS = retornarEnderecosDoCliente(retornarEndereco(idEndereco).getIdCliente());
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

    void validarPessoaEndereco(Integer idCliente) throws BancoDeDadosException, RegraDeNegocioException {
        clienteService.visualizarCliente(idCliente);
    }
}
