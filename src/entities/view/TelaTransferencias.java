package entities.view;

import entities.interfaces.Tela;

import java.util.Scanner;

public class TelaTransferencias implements Tela {
    public static void exibirTransferencias(){
        System.out.println("Você está na Tela de Transferências");
        TelaTransferencias.tratarInput(TelaTransferencias.pedirInput());
    }

    public static void tratarInput(int input) {
        switch(input){
            case 1 ->{
                //pega as contas no banco de dados
                //mostra as contas
                System.out.println("Selecione uma conta para ver as suas Transferências");
                //seleciona uma conta
                //mostra o que tem que mostrar
                exibirTransferencias();
            }
            case 2 -> Tela.redirecionarParaTela(1);
            default -> {
                System.out.println("Opção inválida!");
                exibirTransferencias();
            }
        }
    }

    public static int pedirInput() {
        System.out.println("[1] -> Selecionar uma conta e ver suas transferências\n[2] -> Voltar para a Tela Principal");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }
}
