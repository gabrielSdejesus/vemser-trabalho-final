package entities.view;

import entities.interfaces.Tela;
import entities.model.Cartao;
import entities.model.Conta;
import java.util.Scanner;

public class TelaExtratoCartaoCredito  implements Tela {
    public static void exibirTelaExtratoCartaoCredito(){
        System.out.println("Você está na Tela de Extrato de Cartao de Crédito");
        TelaExtratoCartaoCredito.tratarInput(TelaExtratoCartaoCredito.pedirInput());
    }

    public static void tratarInput(int input) {
        Conta login = null;
        if(input > 0){
            login = Tela.login();
        }
        switch(input){
            case 1 ->{
                if(login != null){
                    Cartao[] cartoes = login.getCartoes();
                    int cartao = 0;

                    for(int i=0;i<cartoes.length;i++){
                        if(cartoes[i] != null && cartoes[i].getTipo() == 2){
                            cartao = i;
                            break;
                        }
                    }

                    if(cartoes[cartao] != null){
                        cartoes[cartao].exibirDadosCartao();
                        cartoes[cartao].exibirCompras();
                    }else{
                        System.out.println("Você não possui nenhum cartão de crédito");
                    }

                }else {
                    System.out.println("Login mal-sucedido");
                }
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
        System.out.println("[1] -> Exibir seu extrato do cartão de crédito\n[2] -> Voltar para a Tela Principal");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }
}
