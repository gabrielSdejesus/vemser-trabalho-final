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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class ContaService extends Servico {
    private final ContaRepository contaRepository;
    private final ClienteService clienteService;
    private final CartaoService cartaoService;
    private final ContatoService contatoService;
    private final EnderecoService enderecoService;
    private final EmailService emailService;

    public ContaService(ContaRepository contaRepository, @Lazy ClienteService clienteService,
                        @Lazy CartaoService cartaoService, @Lazy ContatoService contatoService,
                        @Lazy EnderecoService enderecoService, ObjectMapper objectMapper, EmailService emailService) {
        super(objectMapper);
        this.contaRepository = contaRepository;
        this.clienteService = clienteService;
        this.cartaoService = cartaoService;
        this.contatoService = contatoService;
        this.enderecoService = enderecoService;
        this.emailService = emailService;
    }

    public List<ContaDTO> listar() throws BancoDeDadosException {
        return contaRepository.listar().stream()
                .map(conta -> objectMapper.convertValue(conta, ContaDTO.class))
                .toList();
    }

    public ContaDTO retornarContaCliente(Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException{
        return validandoAcessoConta(numeroConta, senha);
    }

    public ContaDTO criar(ContaCreateDTO contaCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        //Criando e validando se já existe um cliente pelo CPF
        ClienteDTO clienteDTO = clienteService.adicionarCliente(contaCreateDTO.getClienteCreateDTO());

        //Criando conta
        Conta conta = contaRepository.adicionar(criandoDados(contaCreateDTO, clienteDTO));

        //Criando contato
        contaCreateDTO.getContatoCreateDTO().setIdCliente(clienteDTO.getIdCliente());
        contatoService.adicionar(contaCreateDTO.getContatoCreateDTO(), conta.getNumeroConta(), conta.getSenha());

        //Criando endereço
        contaCreateDTO.getEnderecoCreateDTO().setIdCliente(clienteDTO.getIdCliente());
        enderecoService.adicionar(contaCreateDTO.getEnderecoCreateDTO(), conta.getNumeroConta(), conta.getSenha());


        //Criando cartão
        CartaoDTO cartaoDTO = cartaoService.criar(conta.getNumeroConta(), contaCreateDTO.getSenha(), TipoCartao.DEBITO);

        //Email
        emailService.sendEmailCreate(conta, cartaoDTO);
        return objectMapper.convertValue(conta, ContaDTO.class);
    }

    public ContaDTO alterarSenha(String novaSenha, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        //Vericando acesso à conta que irá ser atualizada
        ContaDTO contaDTO = validandoAcessoConta(numeroConta, senha);

        //Alterando senha
        Conta conta = objectMapper.convertValue(contaDTO, Conta.class);
        conta.setSenha(novaSenha);

        //Executando edição
        return objectMapper.convertValue(contaRepository
                .editar(conta.getNumeroConta(), conta), ContaDTO.class);
    }

    public ContaDTO sacar(Double valor, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        //Validando conta
        ContaDTO contaDTO = retornarContaCliente(numeroConta, senha);

        if(valor > contaDTO.getSaldo()){
            throw new RegraDeNegocioException("Operação não realizada, saldo insuficiente! Seu saldo atual: R$" + contaDTO.getSaldo());
        }

        //Sacando valor
        Conta conta = objectMapper.convertValue(contaDTO, Conta.class);
        conta.setSaldo(conta.getSaldo() - valor);
        return objectMapper.convertValue(contaRepository.editar(conta.getNumeroConta(), conta), ContaDTO.class);
    }

    public ContaDTO depositar(Double valor, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        //Validando conta
        ContaDTO contaDTO = retornarContaCliente(numeroConta, senha);

        //Depositando valor
        Conta conta = objectMapper.convertValue(contaDTO, Conta.class);
        conta.setSaldo(conta.getSaldo() + valor);
        return objectMapper.convertValue(contaRepository.editar(conta.getNumeroConta(), conta), ContaDTO.class);
    }

    public boolean reativarConta(String cpf) throws BancoDeDadosException, RegraDeNegocioException {
        Conta contaReativar = contaRepository.listar().stream()
                .filter(conta -> conta.getCliente().getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Este CPF não possui registros!"));

        if(contaReativar.getStatus().getStatus() == 1){
            throw new RegraDeNegocioException("Este CPF já está ativo!");
        }
        return contaRepository.reativarConta(cpf);
    }

    public void removerConta(Integer numeroConta) throws BancoDeDadosException, RegraDeNegocioException {
        //Validando e recuperando conta
        ContaDTO contaAserRemovida = objectMapper.convertValue(contaRepository.consultarNumeroConta(numeroConta), ContaDTO.class);

        if(Objects.isNull(contaAserRemovida)){
            throw new RegraDeNegocioException("Esta conta não existe!");
        }

        if(contaAserRemovida.getStatus().equals(Status.INATIVO)){
            throw new RegraDeNegocioException("Não foi possível remover a conta! A conta já está inativa.");
        }

        //Email
        emailService.sendEmailDelete(contaAserRemovida);

        //Deletando cartoes
        cartaoService.deletarTodosCartoes(numeroConta);

        //Deletando conta
        contaRepository.remover(numeroConta);

        //Deletando cliente
        clienteService.deletarCliente(contaAserRemovida.getCliente().getIdCliente());
    }

    private Conta criandoDados(ContaCreateDTO contaCreateDTO, ClienteDTO clienteDTO){
        Random random = new Random();

        //Convertendo a contaCreate em Conta
        Conta conta = objectMapper.convertValue(contaCreateDTO, Conta.class);

        //Setando na conta o novo cliente criado no banco de dados
        conta.setCliente(objectMapper.convertValue(contaCreateDTO.getClienteCreateDTO(), Cliente.class));

        //Setando o ‘id’ respetivo gerado na criação do cliente
        conta.getCliente().setIdCliente(clienteDTO.getIdCliente());

        //Gerando um número aleatório de 4 dígitos para agência da conta
        conta.setAgencia(random.nextInt(9000) + 1000);
        return conta;
    }

    ContaDTO validandoAcessoConta(Integer numeroConta, String senha) throws RegraDeNegocioException, BancoDeDadosException {
        Conta conta = contaRepository.consultarNumeroConta(numeroConta);

        if(Objects.isNull(conta)){
            throw new RegraDeNegocioException("Conta inválida!");
        }

        if(!conta.getSenha().equals(senha)){
            throw new RegraDeNegocioException("Conta ou Senha inválida!");
        }

        if(conta.getStatus().equals(Status.INATIVO)){
            throw new RegraDeNegocioException("Conta inativa!");
        }

        return objectMapper.convertValue(conta, ContaDTO.class);
    }
}