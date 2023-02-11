package entities.view;

import entities.controller.BancoDeDados;
import entities.interfaces.Tela;
import entities.model.Conta;

import java.util.Scanner;

public class TelaMovimentacoes implements Tela {
    public static void exibirTelaMovimentacoes(){
        System.out.println("Você está na Tela de Transferências");
        TelaMovimentacoes.tratarInput(TelaMovimentacoes.pedirInput());
    }

    public static void tratarInput(int input) {
        Scanner scanner = new Scanner(System.in);
        Conta login;
        String senhaConta;
        double valor;
        switch(input){
            case 1 -> {
                login = Tela.login();
                if(login != null){
                    System.out.println("Insira o valor do Depósito:");
                    valor = Double.parseDouble(scanner.nextLine());

                    System.out.println("Insira a senha da conta:");
                    senhaConta = scanner.nextLine();

                    if(login.depositar(valor, senhaConta)){
                        System.out.println("Depósito concluído!");
                        System.out.printf("Saldo atual: R$ %.2f", login.getSaldo());
                    }else{
                        System.err.println("Depósito não realizado! Senha incorreta!");
                    }
                }else{
                    System.out.println("Login mal-sucedido");
                }
                exibirTelaMovimentacoes();
            }
            case 2 -> {
                login = Tela.login();
                if(login != null){
                    System.out.println("Insira o valor do Saque:");
                    valor = Double.parseDouble(scanner.nextLine());

                    System.out.println("Insira a senha da conta:");
                    senhaConta = scanner.nextLine();

                    if(login.sacar(valor, senhaConta)){
                        System.out.println("Saque concluído!");
                        System.out.printf("Saldo atual: R$ %.2f", login.getSaldo());
                    }else{
                        System.err.println("Saque não realizado! "+(login.verificarSenha(senhaConta)? "Fundos Insuficientes": "Senha incorreta")+"!");
                    }
                }else{
                    System.out.println("Login mal-sucedido");
                }
            }
            case 3 -> {
                login = Tela.login();
                if(login != null){
                    System.out.println("Insira o valor da transferência:");
                    valor = Double.parseDouble(scanner.nextLine());

                    System.out.println("Insira a senha da conta:");
                    senhaConta = scanner.nextLine();

                    System.out.println("Insira o número da conta que receberá a transferência:");
                    String numeroConta = scanner.nextLine();
                    if(login.transferir(BancoDeDados.consultarNumeroDaConta(numeroConta), valor, senhaConta)){
                        System.out.println("Transferência concluída!");
                        System.out.printf("Saldo atual: R$ %.2f", login.getSaldo());
                    }else{
                        String resultado;
                        if(BancoDeDados.consultarNumeroDaConta(numeroConta) != null){
                            if(login.verificarSenha(senhaConta)){
                                resultado = "Saldo insuficiente";
                            }else{
                                resultado = "Senha incorreta";
                            }
                        }else{
                            resultado = "Número da conta inexistente";
                        }
                        System.err.println("Saque não realizado! "+resultado+"!");
                    }
                }else{
                    System.out.println("Login mal-sucedido");
                }
            }
            case 4 -> Tela.redirecionarParaTela(1);
            default -> {
                System.out.println("Opção inválida!");
                exibirTelaMovimentacoes();
            }
        }
    }

    public static int pedirInput() {
        System.out.println("[1] -> Depositar\n[2] -> Sacar\n[3] -> Transferir\n[4] -> Voltar para a Tela Principal");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }
}
