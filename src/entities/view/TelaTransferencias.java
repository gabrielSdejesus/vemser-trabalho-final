package entities.view;

import entities.Exibicao;

import java.util.Scanner;

public class TelaTransferencias implements Tela {
    public void exibirTransferencias(){
        System.out.println("Você está na Tela de Extrato de Cartao de Crédito");
        this.tratarInput(this.pedirInput());
    }

    @Override
    public void tratarInput(int input) {
        switch(input){
            case 1 ->{
                //pega as contas no banco de dados
                //mostra as contas
                System.out.println("Selecione uma conta para ver as suas Transferências");
                //seleciona uma conta
                //mostra o que tem que mostrar
                exibirTransferencias();
            }
            case 2 -> redirecionarParaTela(1);
            default -> {
                System.out.println("Opção inválida!");
                exibirTransferencias();
            }
        }
    }

    @Override
    public int pedirInput() {
        System.out.println("[1] -> Selecionar uma conta e ver suas transferências\n[2] -> Voltar para a Tela Principal");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }
}
