package br.com.dbc.vemser.financeiro.service;

import br.com.dbc.vemser.financeiro.dto.ClienteCreateDTO;
import br.com.dbc.vemser.financeiro.dto.ClienteDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.model.Cliente;
import br.com.dbc.vemser.financeiro.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService extends Servico {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository, ObjectMapper objectMapper) {
        super(objectMapper);
        this.clienteRepository = clienteRepository;
    }

    public ClienteDTO adicionarCliente(ClienteCreateDTO clienteCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        Cliente cliente = objectMapper.convertValue(clienteCreateDTO, Cliente.class);
        return objectMapper.convertValue(clienteRepository.adicionar(cliente), ClienteDTO.class);
    }

    public boolean deletarCliente(int idCliente) throws BancoDeDadosException, RegraDeNegocioException{
        return this.clienteRepository.remover(idCliente);
    }

    public ClienteDTO alterarCliente(int idCliente, ClienteCreateDTO clienteCreateDTO) throws BancoDeDadosException, RegraDeNegocioException{
        Cliente cliente = objectMapper.convertValue(clienteCreateDTO, Cliente.class);
        return objectMapper.convertValue(this.clienteRepository.editar(idCliente, cliente), ClienteDTO.class);
    }

    public ClienteDTO visualizarCliente(int idCliente) throws BancoDeDadosException, RegraDeNegocioException{
        return objectMapper.convertValue(retornarCliente(idCliente), ClienteDTO.class);
    }

    public Cliente retornarCliente(int idCliente) throws BancoDeDadosException, RegraDeNegocioException {
        return this.clienteRepository.consultarPorIdCliente(idCliente);
    }

    public List<ClienteDTO> listarClientes() throws BancoDeDadosException, RegraDeNegocioException{
        return clienteRepository.listar().stream()
                .map(cliente -> objectMapper.convertValue(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }
}
