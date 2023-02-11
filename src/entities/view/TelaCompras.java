package entities.view;

import entities.interfaces.Tela;

import java.util.Scanner;

public class TelaCompras  implements Tela {
    public static void exibirCompras(){
        System.out.println("Você está na Tela de Compras");
        TelaCompras.tratarInput(TelaCompras.pedirInput());
    }

    public static void tratarInput(int input) {
            switch(input){
                case 1 ->{
                    //pega as contas no banco de dados
                    //mostra as contas
                    System.out.println("Selecione uma conta para ver as suas Compras");
                    //seleciona uma conta
                    //mostra o que tem que mostrar
                    exibirCompras();
                }
                case 2 -> Tela.redirecionarParaTela(1);
                default -> {
                    System.out.println("Opção inválida!");
                    exibirCompras();
                }
            }
    }

    public static int pedirInput() {
        System.out.println("[1] -> Selecionar uma conta e ver suas compras\n[2] -> Voltar para a Tela Principal");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }
}
