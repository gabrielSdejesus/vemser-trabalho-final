package entities.service;

import entities.exception.BancoDeDadosException;
import entities.model.*;
import entities.repository.ContaRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

public class ContaService extends Service{

    private ContaRepository contaRepository;

    public ContaService() {
        this.contaRepository = new ContaRepository();
    }

    public Conta adicionar() {
        int agencia = ThreadLocalRandom.current().nextInt(10, 99);
        String senha;

        try {
            Cliente cliente = new ClienteService().adicionarCliente();

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
                System.out.println("Número da sua CONTA: "+conta.getNumeroConta());
            }
            return conta;
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Conta> listar() {
        try {
            List<Conta> contas = contaRepository.listar();
            contas.forEach(System.out::println);
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

    public void removerConta() {
        try{
            List<Conta> contas = this.listar();
            int numeroConta = askInt("Insira o número da CONTA que deseja DELETAR:");
            if(numeroConta != -1){
                Conta conta = new Conta();
                for(Conta c: contas){
                    if(c.getNumeroConta() == numeroConta){
                        conta = c;
                        break;
                    }
                }
                CartaoService cartaoService = new CartaoService();
                for(Cartao cartao:cartaoService.returnCartoes(conta)){
                    cartaoService.deletarCartao(cartao);
                }
                if(contaRepository.remover(numeroConta)) {
                    System.out.println("Conta removida com sucesso!");
                    ClienteService clienteService = new ClienteService();
                    clienteService.deletarCliente(conta.getCliente().getIdCliente());
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
            if(conta != null && conta.getSenha().equals(senhaConta)){
                return conta;
            }else{
                return null;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    public void exibirConta(Conta conta){
        System.out.println("Número da CONTA: "+conta.getNumeroConta());
        System.out.println("Nome do CLIENTE da CONTA: "+conta.getCliente().getNome());
        System.out.println("Saldo: "+conta.getSaldo());
        System.out.println("Agência: "+conta.getAgencia());
        System.out.println("Cheque especial: "+conta.getChequeEspecial());
    }

    public void depositar(Conta conta){
        double valor = askDouble("Insira o valor do Depósito: ");
        if(valor != -1){
            conta.setSaldo(conta.getSaldo()+valor);
            this.editar(conta.getNumeroConta(), conta);
        }else{
            System.err.println("Valor inválido");
        }
    }

    public void sacar(Conta conta){
        double valor = askDouble("Insira o valor do Saque: ");
        if(valor != -1){
            if(conta.getSaldo()-valor+conta.getChequeEspecial() < 0){
                System.err.println("Saldo insuficiente!");
            }else{
                conta.setSaldo(conta.getSaldo()-valor);
                this.editar(conta.getNumeroConta(), conta);
            }
        }else{
            System.err.println("Valor inválido");
        }
    }

    public void transferir(Conta conta){
        double valor = askDouble("Insira o valor da transferência: ");
        if(valor != -1){
            if (valor <= conta.getSaldo()) {
                int numeroConta = askInt("Insira o número da conta que receberá a transferência: ");
                if(numeroConta != -1){
                    try{
                        Conta contaRecebeu = this.contaRepository.consultarPorNumeroConta(numeroConta);

                        try {
                            contaRecebeu.setSaldo(contaRecebeu.getSaldo()+valor);
                        } catch (NullPointerException e) {
                            System.err.println("A conta de destino não existe!");
                        }

                        this.editar(numeroConta, contaRecebeu);

                        conta.setSaldo(conta.getSaldo()-valor);

                        this.editar(conta.getNumeroConta(), conta);

                        System.err.println("Transferência concluída!");
                        System.out.printf("Saldo atual: R$ %.2f\n", conta.getSaldo());
                        TransferenciaService transferenciaService = new TransferenciaService();
                        transferenciaService.adicionarTransferencia(conta, contaRecebeu, valor);
                    }catch(BancoDeDadosException e){
                        e.printStackTrace();
                    }
                }else{
                    System.err.println("Valor inválido");
                }
            } else {
                System.err.println("Saldo insuficiente!");
            }
        }else{
            System.err.println("Valor inválido");
        }
    }

    public void pagar(Conta conta){
        double valor = askDouble("Insira o valor do pagamento: ");
        if(valor != -1){
            CartaoService cartaoService = new CartaoService();
            List<Cartao> cartoes = cartaoService.returnCartoes(conta);
            Cartao cartao;

            StringBuilder message = new StringBuilder("Selecione o cartão para efetuar o pagamento:");
            for(int i=0;i<cartoes.size();i++){
                if(cartoes.get(i) != null){
                    message.append("Cartão [").append(i + 1).append("] -> ").append(cartoes.get(i).getTipo() == TipoCartao.DEBITO ? "Débito" : "Crédito").append(":");
                }
            }
            int input = askInt(String.valueOf(message)) - 1;

            if(input > 0 && input <= cartoes.size()){
                cartao = cartoes.get(input);

                if(cartao.getTipo() == TipoCartao.CREDITO){
                    if(((CartaoDeCredito) cartao).getLimite()-valor < 0){
                        System.err.println("Limite insuficiente!");
                    }else{
                        ((CartaoDeCredito) cartao).setLimite(((CartaoDeCredito) cartao).getLimite()-valor);
                        if(cartaoService.editarCartao(cartao.getNumeroCartao(), cartao)){
                            System.out.println("Limite do cartão de CRÉDITO ATUALIZADO!");
                            System.out.printf("Limite restante: R$%.2f", ((CartaoDeCredito) cartao).getLimite());
                        }else{
                            System.err.println("Problemas ao atualizar o limite do cartão de crédito");
                        }
                    }
                }else{
                    if(conta.getSaldo()-valor < 0){
                        System.out.println("Saldo insuficiente!");
                    }else{
                        conta.setSaldo(conta.getSaldo()-valor);

                        this.editar(conta.getNumeroConta(), conta);

                        System.err.println("Pagamento concluído!");
                        System.out.printf("Saldo atual: R$ %.2f\n", conta.getSaldo());
                    }
                }
            }
        }else{
            System.err.println("Valor inválido");
        }
    }

    public void alterarSenha(Conta conta){
        String novaSenha;

        while (true) {
            System.out.print("Insira a nova senha: ");
            novaSenha = SCANNER.nextLine().trim().replaceAll(" ", "");
            if (novaSenha.length() == 6 && !Pattern.matches("[a-zA-Z!@#$%^&*(),.?\":{}|<>]+", novaSenha)) {
                conta.setSenha(novaSenha);
                break;
            } else if (novaSenha.length() == 0) {
                break;
            }
            System.out.println("A senha não possui 6 digitos ou não é composta apenas por números! Tente novamente.");
        }
        conta.setSenha(novaSenha);

        try{
            if(this.contaRepository.editar(conta.getNumeroConta(), conta)){
                System.out.println("Senha atualizada com sucesso!");
            }else{
                System.err.println("Problema ao atualizar senha!");
            }
        }catch(BancoDeDadosException e){
            e.printStackTrace();
        }
    }
}
