package entities.view;

import entities.Exibicao;

import java.util.Scanner;

public class TelaExtratoCartaoDebito  implements Tela {
    public void exibirTelaExtratoCartaoDebito(){
        System.out.println("Você está na Tela de Extrato de Cartao de Débito");
        this.tratarInput(this.pedirInput());
    }

    @Override
    public void tratarInput(int input) {
        switch(input){
            case 1 ->{
                //pega as contas no banco de dados
                //mostra as contas
                System.out.println("Selecione uma conta para ver o extrato do cartão de débito");
                //seleciona uma conta
                //mostra o que tem que mostrar
                exibirTelaExtratoCartaoDebito();
            }
            case 2 -> redirecionarParaTela(1);
            default -> {
                System.out.println("Opção inválida!");
                exibirTelaExtratoCartaoDebito();
            }
        }
    }

    @Override
    public int pedirInput() {
        System.out.println("[1] -> Selecionar uma conta e ver seu extrato do cartão de débito\n[2] -> Voltar para a Tela Principal");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }
}
