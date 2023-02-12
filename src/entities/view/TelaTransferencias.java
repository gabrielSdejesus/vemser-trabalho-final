package entities.view;

import entities.interfaces.Tela;
import entities.model.Conta;

import java.util.Scanner;

public class TelaTransferencias implements Tela {
    public static void exibirTransferencias(){
        System.out.println("Você está na Tela de Transferências");
        TelaTransferencias.tratarInput(TelaTransferencias.pedirInput());
    }

    public static void tratarInput(int input) {
        Conta login;
        switch(input){
            case 1 ->{
                login = Tela.login();
                if(login != null){
                    login.exibirTransferencias();
                }else{
                    System.out.println("Login mal-sucedido");
                }
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
        System.out.println("[1] -> Insira seus dados de login para VISUALIZAR suas TRANSFERÊNCIAS\n[2] -> Voltar para a Tela Principal");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }
}
