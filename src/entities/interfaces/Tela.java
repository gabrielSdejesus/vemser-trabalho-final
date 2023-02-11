package entities.interfaces;

import entities.controller.BancoDeDados;
import entities.model.Conta;
import entities.view.*;

import java.util.Scanner;

public interface Tela {
    static void tratarInput(int input){
        // cada classe que implementa essa inteface deve refazer esse método da sua forma
    }
    static int pedirInput(){
        // cada classe que implementa essa inteface deve refazer esse método da sua forma
        return 0;
    }
    static Conta login(){
        Scanner scanner = new Scanner(System.in);
        String senhaConta, numeroConta;
        System.out.println("Insira o número da sua conta:");
        numeroConta = scanner.nextLine();
        System.out.println("Insira a senha da sua conta:");
        senhaConta = scanner.nextLine();
        Conta conta = BancoDeDados.consultarNumeroDaConta(numeroConta);
        if(conta != null && conta.verificarSenha(senhaConta)){
            return conta;
        }else{
            System.err.println("Número de conta ou senha inválida");
        }
        return null;
    }
    static void redirecionarParaTela(int tela){
        switch(tela){
            case 1 -> {
                TelaPrincipal.exibirTelaPrincipal();
            }
            case 2 -> {
                TelaCompras.exibirCompras();
            }
            case 3 -> {
                TelaExtratoCartaoCredito.exibirTelaExtratoCartaoCredito();
            }
            case 4 -> {
                TelaExtratoCartaoDebito.exibirTelaExtratoCartaoDebito();
            }
            case 5 -> {
                TelaPerfil.exibirTelaPerfil();
            }
            case 6 -> {
                TelaTransferencias.exibirTransferencias();
            }
            case 7 -> {
                TelaMovimentacoes.exibirTelaMovimentacoes();
            }
            default -> {
                System.err.println("Número da tela incorreta, erro na linha 29 da Inteface Tela");
            }
        }
    };
}
