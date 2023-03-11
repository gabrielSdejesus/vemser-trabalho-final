package br.com.dbc.vemser.financeiro.service;

import br.com.dbc.vemser.financeiro.dto.ClienteCreateDTO;
import br.com.dbc.vemser.financeiro.dto.ClienteDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.model.Cliente;
import br.com.dbc.vemser.financeiro.model.Status;
import br.com.dbc.vemser.financeiro.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService extends Servico {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository, ObjectMapper objectMapper) {
        super(objectMapper);
        this.clienteRepository = clienteRepository;
    }

    public List<ClienteDTO> listarClientes(String login, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        if (login.equals("admin") && senha.equals("abacaxi")) {
            return clienteRepository.listar().stream()
                    .map(cliente -> objectMapper.convertValue(cliente, ClienteDTO.class))
                    .toList();
        }else{
            throw new RegraDeNegocioException("Credenciais de Administrador inválidas!");
        }
    }

    public ClienteDTO visualizarCliente(Integer idCliente) throws BancoDeDadosException, RegraDeNegocioException {
        ClienteDTO clienteDTO = objectMapper.convertValue(clienteRepository.consultarPorIdCliente(idCliente), ClienteDTO.class);
        validarClienteInativo(clienteDTO);
        return clienteDTO;
    }

    protected ClienteDTO retornandoCliente(Integer idCliente) throws BancoDeDadosException {
        return objectMapper.convertValue(clienteRepository.consultarPorIdCliente(idCliente), ClienteDTO.class);
    }

    public ClienteDTO adicionarCliente(ClienteCreateDTO clienteCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        validarClientePorCPF(clienteCreateDTO);
        Cliente cliente = objectMapper.convertValue(clienteCreateDTO, Cliente.class);
        return objectMapper.convertValue(clienteRepository.adicionar(cliente), ClienteDTO.class);
    }

    public ClienteDTO alterarCliente(Integer idCliente, ClienteCreateDTO clienteCreateDTO) throws BancoDeDadosException {
        Cliente cliente = objectMapper.convertValue(clienteCreateDTO, Cliente.class);
        return objectMapper.convertValue(clienteRepository.editar(idCliente, cliente), ClienteDTO.class);
    }

    public void deletarCliente(Integer idCliente) throws BancoDeDadosException, RegraDeNegocioException{
        visualizarCliente(idCliente);
        this.clienteRepository.remover(idCliente);
    }

    void validarClienteInativo(ClienteDTO clienteDTO) throws RegraDeNegocioException {

        if (clienteDTO.getStatus() == Status.INATIVO) {
            throw new RegraDeNegocioException("Cliente Inativo!");
        }

        if(clienteDTO.getCpf() == null){
            throw new RegraDeNegocioException("Este cliente não existe!");
        }
    }

    void validarClientePorCPF(ClienteCreateDTO clienteCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        ClienteDTO clienteDTO = objectMapper.convertValue(clienteRepository.listar().stream()
                .filter(cliente -> cliente.getCpf().equals(clienteCreateDTO.getCpf()))
                .findFirst()
                .orElse(null), ClienteDTO.class);

        if(clienteDTO == null) {
            return;
        }

        if(clienteDTO.getStatus().getStatus() == 0) {
            throw new RegraDeNegocioException("Este CPF está inativo!");
        }

        if(clienteDTO.getStatus().getStatus() == 1){
            throw new RegraDeNegocioException("Este cpf já está registrado e ativo");
        }
    }
}
