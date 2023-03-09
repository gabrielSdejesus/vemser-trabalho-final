package br.com.dbc.vemser.financeiro.service;

import br.com.dbc.vemser.financeiro.dto.*;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.model.Cliente;
import br.com.dbc.vemser.financeiro.model.Conta;
import br.com.dbc.vemser.financeiro.model.Status;
import br.com.dbc.vemser.financeiro.model.TipoCartao;
import br.com.dbc.vemser.financeiro.repository.ContaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ContaService extends Servico {
    private final ContaRepository contaRepository;
    private final ClienteService clienteService;
    private final CartaoService cartaoService;
    private final ContatoService contatoService;
    private final EnderecoService enderecoService;
    private final EmailService emailService;

    public ContaService(ContaRepository contaRepository, ClienteService clienteService,
                        CartaoService cartaoService, ContatoService contatoService,
                        EnderecoService enderecoService, ObjectMapper objectMapper, EmailService emailService) {
        super(objectMapper);
        this.contaRepository = contaRepository;
        this.clienteService = clienteService;
        this.cartaoService = cartaoService;
        this.contatoService = contatoService;
        this.enderecoService = enderecoService;
        this.emailService = emailService;
    }

    public List<ContaDTO> listar() throws BancoDeDadosException, RegraDeNegocioException {
        return contaRepository.listar().stream()
                .map(conta -> objectMapper.convertValue(conta, ContaDTO.class))
                .collect(Collectors.toList());
    }

    public ContaDTO retornarContaCliente(ContaAcessDTO contaAcessDTO) throws BancoDeDadosException, RegraDeNegocioException{
        //Verificando senha e recuperando conta
        return validandoAcessoConta(contaAcessDTO);
    }

    public ContaDTO criar(ContaCreateDTO contaCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        //Lista de objetos para passando no email.
        List<Object> listObjects = new ArrayList<>();
        //Criando e validando se já existe um cliente pelo CPF
        ClienteDTO clienteDTO = clienteService.adicionarCliente(contaCreateDTO.getClienteCreateDTO());
        //Adicionando cliente criado a lista
        listObjects.add(clienteDTO);
        //Criando contato
        contaCreateDTO.getContatoCreateDTO().setIdCliente(clienteDTO.getIdCliente());
        contatoService.adicionar(contaCreateDTO.getContatoCreateDTO());
        //Criando endereço
        contaCreateDTO.getEnderecoCreateDTO().setIdCliente(clienteDTO.getIdCliente());
        enderecoService.adicionar(contaCreateDTO.getEnderecoCreateDTO());
        //Criando lista objects, e recebdo list de dados com conta e cartão
        List<Object> retornoDados = criandoDados(contaCreateDTO, clienteDTO);
        //Adicionando conta e cartão a listObjects
        listObjects.add(retornoDados.get(0));
        listObjects.add(retornoDados.get(1));
        //Criando conta
        Conta conta = (Conta) retornoDados.stream().filter(contas -> contas instanceof Conta).findFirst().orElseThrow();
        ContaDTO contaDTO = objectMapper.convertValue(contaRepository.adicionar(conta), ContaDTO.class);
        //Criando cartão
        CartaoCreateDTO cartaoCreateDTO = (CartaoCreateDTO) retornoDados.stream().filter(cartao -> cartao instanceof CartaoCreateDTO).findFirst().orElseThrow();
        cartaoService.criar(contaDTO.getNumeroConta(), cartaoCreateDTO);
        //Enviando email
        emailService.sendEmailCliente(listObjects);
        return contaDTO;
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

    public void reativarConta(String cpf) throws BancoDeDadosException {
        contaRepository.reativarConta(cpf);
    }

    public void removerConta(Integer idCliente, Integer numeroConta) throws BancoDeDadosException, RegraDeNegocioException {
        //Validando e recuperando conta
        ContaDTO contaDTO = objectMapper.convertValue(contaRepository.consultarNumeroConta(numeroConta), ContaDTO.class);
        Cliente cliente = clienteService.retornandoCliente(contaDTO.getCliente().getIdCliente());
        List<ContatoDTO> contatoDTO = contatoService.listarContatosDoCliente(idCliente);
        List<Cliente> clienteList = new ArrayList<>();
        clienteList.add(cliente);

        if(Objects.isNull(contaDTO)){
            throw new RegraDeNegocioException("Esta conta não existe!");
        }

        if(!(contaDTO.getCliente().getIdCliente().equals(idCliente))){
            throw new RegraDeNegocioException("Esta conta não pertence a esse cliente!");
        }

        if(contaDTO.getStatus().equals(Status.INATIVO)){
            throw new RegraDeNegocioException("Não foi possível remover a conta! A conta já está inativa.");
        }

        //Deletando cartoes
        cartaoService.deletarTodosCartoes(contaDTO.getNumeroConta());

        //Deletando conta
        contaRepository.remover(numeroConta);

        //Deletando cliente
        clienteService.deletarCliente(idCliente);
        emailService.sendEmailClienteDelete(idCliente,contatoDTO);
    }

    private List<Object> criandoDados(ContaCreateDTO contaCreateDTO, ClienteDTO clienteDTO){
        //Lista de objetos para passar dois objetos distintos.
        List<Object> objects = new ArrayList<>();
        Random random = new Random();

        //Convertendo a contaCreate em Conta
        Conta conta = objectMapper.convertValue(contaCreateDTO, Conta.class);
        //Setando na conta o novo cliente criado no banco de dados
        conta.setCliente(objectMapper.convertValue(contaCreateDTO.getClienteCreateDTO(), Cliente.class));
        //Setando o id respectivo gerado na criação do cliente
        conta.getCliente().setIdCliente(clienteDTO.getIdCliente());
        //Gerando um número aleatório de 4 dígitos para agência da conta
        conta.setAgencia(random.nextInt(9000) + 1000);
        objects.add(conta);

        //Criando cartão de débito
        CartaoCreateDTO cartaoCreateDTO = new CartaoCreateDTO();
        cartaoCreateDTO.setTipo(TipoCartao.DEBITO);
        cartaoCreateDTO.setDataExpedicao(LocalDate.now());
        cartaoCreateDTO.setVencimento(cartaoCreateDTO.getDataExpedicao().plusYears(4));
        cartaoCreateDTO.setCodigoSeguranca(random.nextInt(999) + 100);
        objects.add(cartaoCreateDTO);
        return objects;
    }

    ContaDTO validandoAcessoConta(ContaAcessDTO contaAcessDTO) throws RegraDeNegocioException, BancoDeDadosException {

        Conta conta = contaRepository.consultarNumeroConta(contaAcessDTO.getNumeroConta());

        if(Objects.isNull(conta)){
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