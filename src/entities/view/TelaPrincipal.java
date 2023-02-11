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
            case 1 ->{
                //pega as contas no banco de dados
                //mostra as contas
                System.out.println("Selecione uma conta para ver os seus dados");
                //seleciona uma conta
                //mostra o que tem que mostrar
                exibirTelaPrincipal();
            }
            case 2 -> Tela.redirecionarParaTela(1);
            default -> {
                System.out.println("Opção inválida!");
                exibirTelaPrincipal();
            }
        }
    }

    public static int pedirInput() {
        System.out.println("[1] -> Ir para Tela x\n[2] -> Ir para Tela x");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }
}
