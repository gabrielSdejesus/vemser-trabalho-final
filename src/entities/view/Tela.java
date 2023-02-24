package entities.view;

import entities.model.Conta;
import entities.service.ContaService;
import entities.service.Service;

import java.util.Scanner;

public abstract class Tela {
    static Conta login(){
        int numeroConta = Service.askInt("Insira o número da sua conta: ");
        if(numeroConta != -1) {
            String senhaConta = Service.askString("Insira a senha da sua conta: ");
            if (senhaConta.equals("")) {
                ContaService contaService = new ContaService();
                Conta conta = contaService.retornarConta(numeroConta, senhaConta);
                if (conta != null) {
                    return conta;
                } else {
                    System.err.println("Número de conta ou senha inválida");
                }
            }else{
                return null;
            }
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

    protected static int pedirInput(String message){
        System.out.println(message);
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }
}
