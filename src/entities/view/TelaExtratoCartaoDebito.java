package entities.view;

import entities.interfaces.Tela;
import entities.model.Cartao;
import entities.model.Conta;

import java.util.Scanner;

public class TelaExtratoCartaoDebito  implements Tela {
    public static void exibirTelaExtratoCartaoDebito(){
        System.out.println("Você está na Tela de Extrato de Cartao de Débito");
        TelaExtratoCartaoDebito.tratarInput(TelaExtratoCartaoDebito.pedirInput());
    }

    public static void tratarInput(int input) {
        Conta login;
        switch(input){
            case 1 ->{
                login = Tela.login();
                if(login != null){
                    Cartao[] cartoes = login.getCartoes();
                    Cartao cartao = null;

                    for(Cartao c: cartoes){
                        if(c != null && c.getTipo() == 1){
                            cartao = c;
                            break;
                        }
                    }

                    if(cartao != null){
                        cartao.exibirDadosCartao();
                        cartao.exibirCompras();
                    }else{
                        System.out.println("Você não possui nenhum cartão de débito");
                    }

                }else {
                    System.out.println("Login mal-sucedido");
                }
                exibirTelaExtratoCartaoDebito();
            }
            case 2 -> Tela.redirecionarParaTela(1);
            default -> {
                System.out.println("Opção inválida!");
                exibirTelaExtratoCartaoDebito();
            }
        }
    }

    public static int pedirInput() {
        System.out.println("[1] -> Exibir seu extrato do cartão de débito\n[2] -> Voltar para a Tela Principal");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }
}
