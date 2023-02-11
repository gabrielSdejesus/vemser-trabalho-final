package entities.view;

import entities.interfaces.Tela;

import java.util.Scanner;

public class TelaExtratoCartaoCredito  implements Tela {
    public static void exibirTelaExtratoCartaoCredito(){
        System.out.println("Você está na Tela de Extrato de Cartao de Crédito");
        TelaExtratoCartaoCredito.tratarInput(TelaExtratoCartaoCredito.pedirInput());
    }

    public static void tratarInput(int input) {
        switch(input){
            case 1 ->{
                //pega as contas no banco de dados
                //mostra as contas
                System.out.println("Selecione uma conta para ver o extrato do cartão de crédito");
                //seleciona uma conta
                //mostra o que tem que mostrar
                exibirTelaExtratoCartaoCredito();
            }
            case 2 -> Tela.redirecionarParaTela(1);
            default -> {
                System.out.println("Opção inválida!");
                exibirTelaExtratoCartaoCredito();
            }
        }
    }

    public static int pedirInput() {
        System.out.println("[1] -> Selecionar uma conta e ver seu extrato do cartão de crédito\n[2] -> Voltar para a Tela Principal");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }
}
