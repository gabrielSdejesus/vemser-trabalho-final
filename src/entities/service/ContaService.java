package entities.service;

import entities.model.Cartao;
import entities.model.Conta;

public class ContaService extends Service{
    public static void deletarConta(){
        String numeroConta;
        if(BancoDeDados.getContas().size() > 0){
            System.err.println("Atenção! Deletar uma CONTA também deletará seu CLIENTE");
            System.out.println("Contas cadastradas");
            for(Conta conta : BancoDeDados.getContas()) {
                System.out.printf("\tCliente: %s | CPF: %s | Nº da conta %d\n",
                        conta.getCliente().getNome(), conta.getCliente().getCpf(), conta.getNumero());
            }
            System.out.println("\nInsira o NÚMERO da CONTA que quer deletar:");
            numeroConta = SCANNER.nextLine();
            Conta conta = BancoDeDados.consultarNumeroDaConta(numeroConta);
            if(conta != null && conta.getSaldo() > 0){
                String confirmacao;
                System.out.println("Essa CONTA ainda tem saldo, tem certeza dessa operação? [Y/N]");
                confirmacao = SCANNER.nextLine();
                if(confirmacao.equalsIgnoreCase("Y")){
                    System.out.println("\tContinuando operação!");
                }else{
                    System.err.println("\tOperação encerrada!");
                }
            }

            if(BancoDeDados.deletarConta(Integer.parseInt(numeroConta))){
                System.out.println("CONTA e CLIENTE deletados com sucesso!");
            }else{
                System.err.println("NÚMERO de CONTA informado não encontrado no Banco De Dados!");
                System.err.println("Operação não realizada!");
            }
        }else{
            System.err.println("Não há CONTAS cadastradas!");
        }
    }

    public static void exibirConta(Conta conta){
        String senhaConta;
        System.out.println("Insira a senha da sua conta:");
        senhaConta = SCANNER.nextLine();
        if(conta.verificarSenha(senhaConta)){
            conta.exibir();
        }
    }

    public static void depositar(Conta conta){
        double valor;
        String senhaConta;
        System.out.print("Insira o valor do Depósito: ");
        valor = Double.parseDouble(SCANNER.nextLine());

        System.out.print("Insira a senha da conta: ");
        senhaConta = SCANNER.nextLine();

        if(conta.depositar(valor, senhaConta)){
            System.err.println("Depósito concluído!");
            System.out.printf("Saldo atual: R$ %.2f", conta.getSaldo());
        }else{
            System.err.println("Depósito não realizado! Senha incorreta!");
        }
    }

    public static void sacar(Conta conta){
        double valor;
        String senhaConta;
        System.out.print("Insira o valor do Saque: ");
        valor = Double.parseDouble(SCANNER.nextLine());

        System.out.print("Insira a senha da conta: ");
        senhaConta = SCANNER.nextLine();

        if(conta.sacar(valor, senhaConta)){
            System.err.println("Saque concluído!");
            System.out.printf("Saldo atual: R$ %.2f", conta.getSaldo());
        }else{
            System.err.println("Saque não realizado! "+(conta.verificarSenha(senhaConta)? "Fundos Insuficientes": "Senha incorreta")+"!");
        }
    }

    public static void transferir(Conta conta){
        double valor;
        String senhaConta;
        System.out.print("Insira o valor da transferência: ");
        valor = Double.parseDouble(SCANNER.nextLine());

        System.out.print("Insira a senha da conta: ");
        senhaConta = SCANNER.nextLine();

        System.out.print("Insira o número da conta que receberá a transferência: ");
        String numeroConta = SCANNER.nextLine();
        if(conta.transferir(BancoDeDados.consultarNumeroDaConta(numeroConta), valor, senhaConta)){
            System.err.println("Transferência concluída!");
            System.out.printf("Saldo atual: R$ %.2f\n", conta.getSaldo());
        }else{
            String resultado;
            if(BancoDeDados.consultarNumeroDaConta(numeroConta) != null){
                if(conta.verificarSenha(senhaConta)){
                    resultado = "Saldo insuficiente!";
                }else{
                    resultado = "Senha incorreta";
                }
            }else{
                resultado = "Número da conta inexistente";
            }
            System.err.println(resultado);
        }
    }

    public static void pagar(Conta conta){
        double valor;
        String senhaConta;
        System.out.print("Insira o valor do pagamento: ");
        valor = Double.parseDouble(SCANNER.nextLine());

        Cartao[] cartoes = conta.getCartoes();
        Cartao cartao;

        System.out.println("Selecione o cartão para efetuar a compra:");
        for(int i=0;i<cartoes.length;i++){
            if(cartoes[i] != null){
                System.out.printf("Cartão [%d] -> %s: ", (i+1), (cartoes[i].getTipo() == 1 ? "Débito":"Crédito"));
            }
        }
        cartao = cartoes[Integer.parseInt(SCANNER.nextLine())-1];

        System.out.print("Insira a senha da conta: ");
        senhaConta = SCANNER.nextLine();
        if(cartao.pagarComCartao(valor, senhaConta)){
            System.err.println("Operação realizada com sucesso!");
        }else{
            if(conta.getSaldo() < valor){
                System.err.println("Saldo indisponível!");
            }else{
                System.err.println("Senha Incorreta!");
            }
        }
    }

    public static void alterarSenha(){
        String cpfOuNumero, senhaAntiga, novaSenha;

        System.out.print("Insira o CPF do CLIENTE ou o NÚMERO da CONTA: ");
        cpfOuNumero = SCANNER.nextLine();

        if(BancoDeDados.consultarNumeroDaConta(cpfOuNumero) != null || BancoDeDados.consultarExistenciaPorCPF(cpfOuNumero) != null){
            System.out.print("Insira a SENHA ANTIGA: ");
            senhaAntiga = SCANNER.nextLine();
            Conta conta = (BancoDeDados.consultarNumeroDaConta(cpfOuNumero) != null)? BancoDeDados.consultarNumeroDaConta(cpfOuNumero):BancoDeDados.consultarExistenciaPorCPF(cpfOuNumero);
            if(conta.verificarSenha(senhaAntiga)){
                System.out.print("Insira a nova senha: ");
                novaSenha = SCANNER.nextLine();
                conta.alterarSenha(senhaAntiga, novaSenha);
            }else{
                System.err.println("Senha incorreta!");
                System.err.println("Se não lembra a sua SENHA fale com os ADMINISTRADORES do BANCO para resolver seu problema!");
            }
        }else{
            System.err.println("CPF do CLIENTE ou NÚMERO da CONTA incorreto!");
        }
    }
}
