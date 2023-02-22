package entities.view;

import entities.controller.BancoDeDados;
import entities.model.Conta;
import entities.view.*;

import java.util.Scanner;

public abstract class Tela {
    static Conta login(){
        Scanner scanner = new Scanner(System.in);
        String senhaConta, numeroConta;
        System.out.print("Insira o número da sua conta: ");
        numeroConta = scanner.nextLine();
        System.out.print("Insira a senha da sua conta: ");
        senhaConta = scanner.nextLine();
        Conta conta = BancoDeDados.consultarNumeroDaConta(numeroConta);
        if(conta != null && conta.verificarSenha(senhaConta)){
            return conta;
        }else{
            System.err.println("Número de conta ou senha inválida");
        }
        return null;
    }

    static boolean loginAdm(){
        Scanner scanner = new Scanner(System.in);
        String senhaAdm;
        System.out.println("Insira a senha Administrativa [ABACAXI]:");
        senhaAdm = scanner.nextLine();
        return senhaAdm.equals("ABACAXI");
    }

    static void redirecionarParaTela(int tela){
        System.out.println("\n");
        switch(tela){
            case 1 -> TelaPrincipal.exibirTelaPrincipal();
            case 2 -> TelaCompras.exibirCompras();
            case 3 -> TelaCartao.exibirTelaCartao();
            case 4 -> TelaPerfil.exibirTelaPerfil();
            case 5 -> TelaTransferencias.exibirTransferencias();
            case 6 -> TelaMovimentacoes.exibirTelaMovimentacoes();
            case 7 -> TelaAdministrador.exibirTelaAdministrador();
            default -> System.err.println("Número da tela incorreta, erro na Inteface Tela");
        }
    }
}
