package br.com.dbc.vemser.financeiro.service;

import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.model.*;
import br.com.dbc.vemser.financeiro.repository.ContaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

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

    public Conta adicionar() {
        int agencia = ThreadLocalRandom.current().nextInt(1000, 9999);
        String senha;

        try {
            Cliente cliente = clienteService.adicionarCliente();

            while (true) {
                System.out.print("Insira a senha: ");
                senha = SCANNER.nextLine().trim().replaceAll(" ", "");
                if (senha.length() == 6 && !Pattern.matches("[a-zA-Z!@#$%^&*(),.?\":{}|<>]+", senha)) {
                    break;
                }
                System.out.println("A senha não possui 6 digitos ou não é composta apenas por números! Tente novamente.");
            }

            Conta conta = new Conta();
            conta.setAgencia(agencia);
            conta.setCliente(cliente);
            conta.setSenha(senha);
            conta.setSaldo(0d);
            conta.setStatus(Status.ATIVO);
            conta.setChequeEspecial(0d);

            conta = contaRepository.adicionar(conta);
            if(conta != null){
                System.err.println("Número da sua conta: "+conta.getNumeroConta());
            }
            return conta;
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Conta> listar() {
        try {
            System.out.println();
            List<Conta> contas = contaRepository.listar();
            contas.forEach(System.out::println);
            System.out.println();
            return contas;
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void editar(Integer numeroConta, Conta conta) {
        try {
            contaRepository.editar(numeroConta, conta);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }

    }

    //função exclusiva do administrador
    public void removerConta() {
        try{
            List<Conta> contas = this.listar();
            int numeroConta = askInt("Insira o número da conta que deseja remover:");

            if(numeroConta != -1){
                Conta conta = new Conta();
                for(Conta c: contas){
                    if(c.getNumeroConta() == numeroConta){
                        conta = c;
                        break;
                    }
                }

                //verificar se a conta já está inativa
                if(conta.getStatus() == Status.INATIVO){
                    System.err.println("\nEstá conta já está inativa!");
                    return;
                }

                //deletar cartão
                List<Cartao> cartoes = cartaoService.returnCartoes(conta);
                if(cartoes != null){
                    for(Cartao cartao: cartoes){
                        cartaoService.deletarCartao(cartao);
                    }
                }

                //deletar conta
                if(contaRepository.remover(numeroConta)) {
                    clienteService.deletarCliente(conta.getCliente().getIdCliente());
                    Servico.tempoParaExibir(100);
                    System.err.println("Conta removida com sucesso!");
                }
            }


        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public Conta retornarConta(int numeroConta, String senhaConta){
        Conta conta;
        Integer numero = numeroConta;
        try{
            conta = this.contaRepository.consultarPorNumeroConta(numero);
            if(conta != null && conta.getSenha() != null && conta.getSenha().equals(senhaConta) && conta.getStatus() == Status.ATIVO){
                return conta;
            }else{

                //retorna null se não existe conta alguma ou algum dado está errado
                if(conta == null){
                    System.err.println("Número de conta ou senha inválida!");
                    return null;
                }

                //a conta já existiu porém ela está inativa
                if(conta.getStatus() == Status.INATIVO){
                    System.err.println("Conta desativada!\n");
                    return null;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void exibirConta(Conta conta){
        System.out.println("\n" + "\tNúmero da conta: "+conta.getNumeroConta());
        System.out.println("\tNome do cliente da conta: "+conta.getCliente().getNome());
        System.out.println("\tSaldo: "+conta.getSaldo());
        System.out.println("\tAgência: "+conta.getAgencia());
        System.out.println("\tCheque especial: "+conta.getChequeEspecial() + "\n");
    }

    public void depositar(Conta conta){
        double valor = askDouble("Insira o valor do Depósito: ");
        if(valor > 0){
            conta.setSaldo(conta.getSaldo()+valor);
            this.editar(conta.getNumeroConta(), conta);
            System.err.println("\nDepósito concluído!");
        }else{
            System.err.println("Valor inválido");
        }
    }

    public void sacar(Conta conta){
        double valor = askDouble("Insira o valor do Saque: ");
        if(valor > 0){
            if(conta.getSaldo()-valor+conta.getChequeEspecial() < 0){
                System.err.println("\nSaldo insuficiente!");
            }else{
                conta.setSaldo(conta.getSaldo()-valor);
                this.editar(conta.getNumeroConta(), conta);
                System.err.println("\nSaque concluído!");
            }
        }else{
            System.err.println("\nValor inválido");
        }
    }

    public void transferir(Conta conta){
        double valor = askDouble("Insira o valor da transferência: ");
        if(valor > 0){
            if (valor <= conta.getSaldo()) {
                int numeroConta = askInt("Insira o número da conta que receberá a transferência: ");
                if(numeroConta > 0){
                    try{
                        Conta contaRecebeu = this.contaRepository.consultarPorNumeroConta(numeroConta);

                        if(contaRecebeu != null){
                            try {
                                contaRecebeu.setSaldo(contaRecebeu.getSaldo()+valor);
                            } catch (NullPointerException e) {
                                System.err.println("A conta de destino não existe!");
                            }

                            this.editar(numeroConta, contaRecebeu);

                            conta.setSaldo(conta.getSaldo()-valor);

                            this.editar(conta.getNumeroConta(), conta);

                            System.err.printf("\nSaldo atual: R$ %.2f", conta.getSaldo());
                            transferenciaService.adicionarTransferencia(conta, contaRecebeu, valor);
                        }else{
                            System.err.println("\nA conta de destino não existe!");
                        }
                    }catch(BancoDeDadosException e){
                        e.printStackTrace();
                    }
                }else{
                    System.err.println("\nValor inválido");
                }
            } else {
                System.err.println("\nSaldo insuficiente!");
            }
        }else{
            System.err.println("\nValor inválido");
        }
    }

    public void pagar(Conta conta){

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
