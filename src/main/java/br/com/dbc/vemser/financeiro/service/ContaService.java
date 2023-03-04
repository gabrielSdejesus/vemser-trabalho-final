package br.com.dbc.vemser.financeiro.service;

import br.com.dbc.vemser.financeiro.dto.ContaCreateDTO;
import br.com.dbc.vemser.financeiro.dto.ContaDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.model.*;
import br.com.dbc.vemser.financeiro.repository.ContaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ContaService extends Servico {
    private final ContaRepository contaRepository;
    private final ClienteService clienteService;
    private final CartaoService cartaoService;
    private final TransferenciaService transferenciaService;

    public ContaService(ContaRepository contaRepository, ClienteService clienteService, CartaoService cartaoService, TransferenciaService transferenciaService, ObjectMapper objectMapper) {
        super(objectMapper);
        this.contaRepository = contaRepository;
        this.clienteService = clienteService;
        this.cartaoService = cartaoService;
        this.transferenciaService = transferenciaService;
    }

    public ContaDTO adicionar(ContaCreateDTO contaCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        Conta conta = objectMapper.convertValue(contaCreateDTO, Conta.class);
        return objectMapper.convertValue(contaRepository.adicionar(conta), ContaDTO.class);
    }

    public List<ContaDTO> listar() throws BancoDeDadosException, RegraDeNegocioException {
        return contaRepository.listar().stream()
                .map(conta -> objectMapper.convertValue(conta, ContaDTO.class))
                .collect(Collectors.toList());
    }

    public ContaDTO editar(Integer numeroConta, ContaCreateDTO contaCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        Conta conta = objectMapper.convertValue(contaCreateDTO, Conta.class);
        return objectMapper.convertValue(contaRepository.editar(numeroConta, conta), ContaDTO.class);
    }

    //função exclusiva do administrador
    public boolean removerConta(Integer numeroConta) throws BancoDeDadosException, RegraDeNegocioException {
        return contaRepository.remover(numeroConta);
    }

    public ContaDTO retornarLoginConta(Integer numeroConta, String senhaConta) throws BancoDeDadosException, RegraDeNegocioException{
        Conta conta = retornarConta(numeroConta);
        if (conta != null && conta.getSenha() != null && conta.getSenha().equals(senhaConta) && conta.getStatus() == Status.ATIVO) {
            return objectMapper.convertValue(conta, ContaDTO.class);
        } else {
            //retorna null se não existe conta alguma ou algum dado está errado
            if (conta == null) {
                throw new RegraDeNegocioException("Número da conta ou Senha inválida!");
            } else if (conta.getStatus() == Status.INATIVO) {//a conta já existiu porém ela está inativa
                throw new RegraDeNegocioException("Conta inativa!");
            }
        }
        return objectMapper.convertValue(conta, ContaDTO.class);
    }

    public Conta retornarConta(Integer numeroConta) throws BancoDeDadosException, RegraDeNegocioException{
        return this.contaRepository.consultarPorNumeroConta(numeroConta);
    }

    public ContaDTO depositar(Integer numeroConta, Double valor) throws BancoDeDadosException, RegraDeNegocioException{
        Conta conta = retornarConta(numeroConta);
        if(conta != null){
            if(conta.getStatus() != Status.ATIVO){
                if(valor <= 0){
                    throw new RegraDeNegocioException("Valor de depósito inválido!");
                }else{
                    conta.setSaldo(conta.getSaldo()+valor);
                    return editar(numeroConta, objectMapper.convertValue(conta, ContaCreateDTO.class));
                }
            }else{
                throw new RegraDeNegocioException("Conta inativada!");
            }
        }else{
            throw new RegraDeNegocioException("Número da conta inválida!");
        }
    }

    public ContaDTO sacar(Integer numeroConta, Double valor) throws BancoDeDadosException, RegraDeNegocioException {
        Conta conta = retornarConta(numeroConta);
        if(conta != null){
            if(conta.getStatus() != Status.ATIVO){
                if(valor <= 0){
                    throw new RegraDeNegocioException("Valor de saque inválido!");
                }else{
                    if(conta.getSaldo()-valor < 0){
                        throw new RegraDeNegocioException("Saldo insuficiente para saque!");
                    }else{
                        conta.setSaldo(conta.getSaldo()-valor);
                        return editar(numeroConta, objectMapper.convertValue(conta, ContaCreateDTO.class));
                    }
                }
            }else{
                throw new RegraDeNegocioException("Conta inativada!");
            }
        }else{
            throw new RegraDeNegocioException("Número da conta inválida!");
        }
    }

    public boolean transferir(Integer numeroContaEnvia, Integer numeroContaRecebe, Double valor) throws BancoDeDadosException, RegraDeNegocioException {
        Conta recebe = retornarConta(numeroContaRecebe),
                envia = retornarConta(numeroContaEnvia);
        if(recebe != null && envia != null){
            if(recebe.getStatus() == Status.ATIVO && envia.getStatus() == Status.ATIVO){
                if(valor > 0){
                    if(envia.getSaldo()-valor >= 0){
                        envia.setSaldo(envia.getSaldo()-valor);
                        recebe.setSaldo(recebe.getSaldo()+valor);
                        editar(numeroContaEnvia, objectMapper.convertValue(envia, ContaCreateDTO.class));
                        editar(numeroContaRecebe, objectMapper.convertValue(recebe, ContaCreateDTO.class));
                        return true;
                    }else{
                        throw new RegraDeNegocioException("Saldo da conta que envia insuficiente!");
                    }
                }else{
                    throw new RegraDeNegocioException("Valor de transferência inválido!");
                }
            }else{
                if(recebe.getStatus() == Status.INATIVO){
                    throw new RegraDeNegocioException("Conta que recebe inativa!");
                }else{
                    throw new RegraDeNegocioException("Conta que envia inativa!");
                }
            }
        }else{
            if(recebe == null){
                throw new RegraDeNegocioException("Número da conta que recebe incorreto!");
            }else{
                throw new RegraDeNegocioException("Número da conta que envia incorreto!");
            }
        }
    }

    public boolean pagar(Conta conta){

        double valor = askDouble("Insira o valor do pagamento: ");
        if(valor > 0){
            List<Cartao> cartoes = cartaoService.returnCartoes(conta);
            Cartao cartao;

            StringBuilder message = new StringBuilder("Selecione um cartão para efetuar o pagamento:\n");
            for(int i=0;i<cartoes.size();i++){
                if(cartoes.get(i) != null){
                    message.append("Cartão [").append(i + 1).append("] -> ").append(cartoes.get(i).getTipo() == TipoCartao.DEBITO ? "Débito" : "Crédito");
                }
                //pular uma linha para exibição
                if(i == 0){
                    message.append("\n");
                }
            }
            int input = askInt(String.valueOf(message)) - 1;

            if(input >= 0 && input <= cartoes.size()){
                cartao = cartoes.get(input);

                if(cartao.getTipo() == TipoCartao.CREDITO){
                    if(((CartaoDeCredito) cartao).getLimite()-valor < 0){
                        System.err.println("\nLimite insuficiente!");
                    }else{
                        ((CartaoDeCredito) cartao).setLimite(((CartaoDeCredito) cartao).getLimite()-valor);
                        if(cartaoService.editarCartao(cartao.getNumeroCartao(), cartao)){
                            System.out.println("\nLimite do cartão de crédito atualizado!");
                            System.out.printf("\nLimite restante: R$%.2f", ((CartaoDeCredito) cartao).getLimite());
                        }else{
                            System.err.println("\nProblemas ao atualizar o limite do cartão de crédito");
                        }
                    }
                }else{
                    if(conta.getSaldo()-valor < 0){
                        System.out.println("\nSaldo insuficiente!");
                    }else{
                        conta.setSaldo(conta.getSaldo()-valor);

                        this.editar(conta.getNumeroConta(), conta);

                        System.err.println("\nPagamento concluído!");
                        System.err.printf("Saldo atual: R$ %.2f\n", conta.getSaldo());
                    }
                }
            }
        }
    }

    public void alterarSenha(Conta conta){
        String novaSenha;

        while (true) {
            System.out.print("Insira a nova senha: ");
            novaSenha = SCANNER.nextLine().trim().replaceAll(" ", "");
            if (novaSenha.length() == 6 && novaSenha.matches("[0-9]+")) {
                conta.setSenha(novaSenha);
                break;
            } else if (novaSenha.length() == 0) {
                break;
            }
            System.err.println("\nA senha não possui 6 digitos ou não é composta apenas por números! Tente novamente.");
        }
        conta.setSenha(novaSenha);

        try{
            if(this.contaRepository.editar(conta.getNumeroConta(), conta)){
                System.err.println("\nSenha atualizada com sucesso!");
            }else{
                System.err.println("\nProblema ao atualizar senha!");
            }
        }catch(BancoDeDadosException e){
            e.printStackTrace();
        }
    }

    public void reativarConta(){
        String cpf = askString("\nInsira o CPF para reativar a conta e o cliente:");
        try{
            if(this.contaRepository.reativarConta(cpf)){
                System.err.println("\nConta e cliente reativados!");
            }else{
                System.err.println("ERR: Erro ao reativar conta e cliente!");
            }
        }catch(BancoDeDadosException e){
            e.printStackTrace();
        }
    }
}
