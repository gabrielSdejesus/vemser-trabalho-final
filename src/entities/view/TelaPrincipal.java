package entities.view;

import entities.interfaces.Tela;

import java.util.Scanner;

public class TelaPrincipal  implements Tela {
    public static void exibirTelaPrincipal(){
        System.out.println("Você está na Tela Principal");
        TelaPrincipal.tratarInput(TelaPrincipal.pedirInput());
    }

    public static void tratarInput(int input) {
        switch(input){
            case 1 -> Tela.redirecionarParaTela(2);
            case 2 -> Tela.redirecionarParaTela(3);
            case 3 -> Tela.redirecionarParaTela(4);
            case 4 -> Tela.redirecionarParaTela(5);
            case 5 -> Tela.redirecionarParaTela(6);
            default -> {
                System.out.println("Programa encerrado!");
            }
        }
    }

    public static int pedirInput() {
        System.out.println("[1] -> Ir para Tela De Compras\n[2] -> Ir para Tela do Extrato do Cartão de Crédito\n[3] -> Ir para Tela do Extrato do Cartão de Débito\n[4] -> Ir para Tela de Perfil\n[5] -> Ir para Tela de Transferências\n[6] -> Encerrar o programa");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }
}
