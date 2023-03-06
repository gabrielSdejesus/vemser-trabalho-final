package br.com.dbc.vemser.financeiro.service;

import br.com.dbc.vemser.financeiro.dto.*;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.model.Cliente;
import br.com.dbc.vemser.financeiro.model.Conta;
import br.com.dbc.vemser.financeiro.model.Status;
import br.com.dbc.vemser.financeiro.repository.ContaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ContaService extends Servico {
    private final ContaRepository contaRepository;
    private final ClienteService clienteService;
    private final CartaoService cartaoService;
    private final TransferenciaService transferenciaService;

    public ContaService(ContaRepository contaRepository, ClienteService clienteService,
                        CartaoService cartaoService, TransferenciaService transferenciaService,
                        ObjectMapper objectMapper) {
        super(objectMapper);
        this.contaRepository = contaRepository;
        this.clienteService = clienteService;
        this.cartaoService = cartaoService;
        this.transferenciaService = transferenciaService;
    }

    public List<ContaDTO> listar() throws BancoDeDadosException, RegraDeNegocioException {
        return contaRepository.listar().stream()
                .map(conta -> objectMapper.convertValue(conta, ContaDTO.class))
                .collect(Collectors.toList());
    }

    public ContaDTO retornarContaCliente(ContaAcessDTO contaAcessDTO) throws BancoDeDadosException, RegraDeNegocioException{
        //Verificando senha e recuperando conta
        ContaDTO contaDTO = validandoAcessoConta(contaAcessDTO);

        return contaDTO;
    }

    public ContaDTO criar(ContaCreateDTO contaCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        //Validando se o CPF já possui uma conta no banco.
        validarCriacaoConta(contaCreateDTO);
        //Criando entidade
        Conta conta = criandoEntidade(contaCreateDTO);
        return objectMapper.convertValue(contaRepository.adicionar(conta), ContaDTO.class);
    }

    public ContaDTO alterarSenha(ContaUpdateDTO contaUpdateDTO) throws BancoDeDadosException, RegraDeNegocioException {

        //Vericando acesso a conta que irá ser atualizada
        ContaAcessDTO contaAcessDTO = new ContaAcessDTO();
        contaAcessDTO.setNumeroConta(contaUpdateDTO.getNumeroConta());
        contaAcessDTO.setSenha(contaUpdateDTO.getSenha());
        ContaDTO contaDTO = validandoAcessoConta(contaAcessDTO);

        //Alterando senha
        Conta conta = objectMapper.convertValue(contaDTO, Conta.class);
        conta.setSenha(contaUpdateDTO.getSenhaNova());

        //Executando edição
        return objectMapper.convertValue(contaRepository
                .editar(conta.getNumeroConta(), conta), ContaDTO.class);
    }

    public ContaDTO sacar(ContaTransfDTO contaTransfDTO) throws BancoDeDadosException, RegraDeNegocioException {
        //Validando conta
        ContaDTO contaDTO = retornarContaCliente(contaTransfDTO);

        if(!(contaDTO.getSaldo() - contaTransfDTO.getValor() >= 0)){
            throw new RegraDeNegocioException("Operação não realizada, saldo insuficiente! Seu saldo atual: R$" + contaDTO.getSaldo());
        }

        //Sacando valor
        Conta conta = objectMapper.convertValue(contaDTO, Conta.class);
        conta.setSaldo(conta.getSaldo() - contaTransfDTO.getValor());
        return objectMapper.convertValue(contaRepository.editar(conta.getNumeroConta(), conta), ContaDTO.class);
    }

    public ContaDTO depositar(ContaTransfDTO contaTransfDTO) throws BancoDeDadosException, RegraDeNegocioException {
        //Validando conta
        ContaDTO contaDTO = retornarContaCliente(contaTransfDTO);

        //Depositando valor
        Conta conta = objectMapper.convertValue(contaDTO, Conta.class);
        conta.setSaldo(conta.getSaldo() + contaTransfDTO.getValor());
        return objectMapper.convertValue(contaRepository.editar(conta.getNumeroConta(), conta), ContaDTO.class);
    }

    public boolean reativarConta(String cpf) throws BancoDeDadosException {
        return contaRepository.reativarConta(cpf);
    }

    public void removerConta(Integer idCliente, Integer numeroConta) throws BancoDeDadosException, RegraDeNegocioException {
        //Validando e recuperando conta
        ContaDTO contaDTO = objectMapper.convertValue(contaRepository.consultarNumeroConta(numeroConta), ContaDTO.class);

        if(!(contaDTO.getCliente().getIdCliente().equals(idCliente))){
            throw new RegraDeNegocioException("Esta conta não pertence a esse cliente!");
        }

        if(contaDTO.getStatus().equals(Status.INATIVO)){
            throw new RegraDeNegocioException("Não foi possível remover conta! Conta já inativa.");
        }

        //Deletando cliente
        clienteService.deletarCliente(idCliente);

        //Deletando conta
        contaRepository.remover(numeroConta);
    }

    private Conta criandoEntidade(ContaCreateDTO contaCreateDTO){
        Random random = new Random();
        Cliente cliente = new Cliente();
        cliente.setIdCliente(contaCreateDTO.getIdCliente());
        Conta conta = new Conta();
        conta.setCliente(cliente);
        conta.setSenha(contaCreateDTO.getSenha());
        conta.setSaldo(contaCreateDTO.getSaldo());
        conta.setAgencia(random.nextInt(9000) + 1000);
        return conta;
    }

    private void validarCriacaoConta(ContaCreateDTO contaCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        ClienteDTO clienteDTO = clienteService.visualizarCliente(contaCreateDTO.getIdCliente());
        if(listar().stream()
                .anyMatch(conta -> conta.getCliente().getCpf().equals(clienteDTO.getCpf()))){
            throw new RegraDeNegocioException("Este cliente já tem uma conta cadastrada!");
        }

        if(!contaCreateDTO.getCpf().equals(clienteDTO.getCpf())){
            throw new RegraDeNegocioException("CPF inválido!");
        }
    }

    private ContaDTO validandoAcessoConta(ContaAcessDTO contaAcessDTO) throws RegraDeNegocioException, BancoDeDadosException {

        Conta conta = contaRepository.consultarNumeroConta(contaAcessDTO.getNumeroConta());
        if(!(conta != null)){
            throw new RegraDeNegocioException("Conta inválida!");
        }

        if(!conta.getSenha().equals(contaAcessDTO.getSenha())){
            throw new RegraDeNegocioException("Conta ou Senha inválida!");
        }

        if(conta.getStatus().equals(Status.INATIVO)){
            throw new RegraDeNegocioException("Conta inativa!");
        }

        return objectMapper.convertValue(conta, ContaDTO.class);
    }
}

