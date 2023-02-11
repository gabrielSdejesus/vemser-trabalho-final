package entities.view;

import entities.interfaces.Tela;

import java.util.Scanner;

public class TelaPerfil  implements Tela {
    public void exibirTelaPerfil(){
        System.out.println("Você está na Tela de Perfil");
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
                exibirTelaPerfil();
            }
            case 2 -> redirecionarParaTela(1);
            default -> {
                System.out.println("Opção inválida!");
                exibirTelaPerfil();
            }
        }
    }

    @Override
    public int pedirInput() {
        System.out.println("[1] -> Selecionar um cliente para ver seus dados\n[2] -> Voltar para a Tela Principal");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }
}
