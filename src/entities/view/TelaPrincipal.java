package entities.view;

import entities.Exibicao;

import java.util.Scanner;

public class TelaPrincipal  implements Tela {
    public void exibirTelaPrincipal(){
        System.out.println("Você está na Tela Principal");
        this.tratarInput(this.pedirInput());
    }

    @Override
    public void tratarInput(int input) {
        switch(input){
            case 1 ->{
                //pega as contas no banco de dados
                //mostra as contas
                System.out.println("Selecione uma conta para ver os seus dados");
                //seleciona uma conta
                //mostra o que tem que mostrar
                exibirTelaPrincipal();
            }
            case 2 -> redirecionarParaTela(1);
            default -> {
                System.out.println("Opção inválida!");
                exibirTelaPrincipal();
            }
        }
    }

    @Override
    public int pedirInput() {
        System.out.println("[1] -> Ir para Tela x\n[2] -> Ir para Tela x");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }
}
