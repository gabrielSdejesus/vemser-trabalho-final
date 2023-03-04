package br.com.dbc.vemser.financeiro.service;

import br.com.dbc.vemser.financeiro.dto.ClienteCreateDTO;
import br.com.dbc.vemser.financeiro.dto.ClienteDTO;
import br.com.dbc.vemser.financeiro.dto.ContatoDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.model.Cliente;
import br.com.dbc.vemser.financeiro.model.Status;
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

    public List<ClienteDTO> listarClientes() throws BancoDeDadosException, RegraDeNegocioException {
        return clienteRepository.listar().stream()
                .map(cliente -> objectMapper.convertValue(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }

    public ClienteDTO visualizarCliente(Integer idCliente) throws BancoDeDadosException, RegraDeNegocioException {
        ClienteDTO clienteDTO = objectMapper.convertValue(clienteRepository.consultarPorIdCliente(idCliente), ClienteDTO.class);
        validarCliente(clienteDTO);
        return clienteDTO;
    }

    public ClienteDTO adicionarCliente(ClienteCreateDTO clienteCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        Cliente cliente = objectMapper.convertValue(clienteCreateDTO, Cliente.class);
        return objectMapper.convertValue(clienteRepository.adicionar(cliente), ClienteDTO.class);
    }

    public ClienteDTO alterarCliente(Integer idCliente, ClienteCreateDTO clienteCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        Cliente cliente = objectMapper.convertValue(clienteCreateDTO, Cliente.class);
        return objectMapper.convertValue(clienteRepository.editar(idCliente, cliente), ClienteDTO.class);
    }

    public boolean deletarCliente(Integer idCliente) throws BancoDeDadosException, RegraDeNegocioException{
        visualizarCliente(idCliente);
        return this.clienteRepository.remover(idCliente);
    }

    private void validarCliente(ClienteDTO clienteDTO) throws RegraDeNegocioException {
        if (clienteDTO.getStatus() == Status.INATIVO) {
            throw new RegraDeNegocioException("INATIVO");
        }
    }
}
