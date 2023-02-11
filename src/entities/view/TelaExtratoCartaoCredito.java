package entities.view;

import entities.Exibicao;

import java.util.Scanner;

public class TelaExtratoCartaoCredito  implements Tela {
    public void exibirTelaExtratoCartaoCredito(){
        System.out.println("Você está na Tela de Extrato de Cartao de Crédito");
        this.tratarInput(this.pedirInput());
    }

    @Override
    public void tratarInput(int input) {
        switch(input){
            case 1 ->{
                //pega as contas no banco de dados
                //mostra as contas
                System.out.println("Selecione uma conta para ver o extrato do cartão de crédito");
                //seleciona uma conta
                //mostra o que tem que mostrar
                exibirTelaExtratoCartaoCredito();
            }
            case 2 -> redirecionarParaTela(1);
            default -> {
                System.out.println("Opção inválida!");
                exibirTelaExtratoCartaoCredito();
            }
        }
    }

    @Override
    public int pedirInput() {
        System.out.println("[1] -> Selecionar uma conta e ver seu extrato do cartão de crédito\n[2] -> Voltar para a Tela Principal");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }
}
