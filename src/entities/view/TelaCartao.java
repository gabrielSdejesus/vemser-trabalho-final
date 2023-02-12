package entities.view;

import entities.interfaces.Tela;
import entities.model.Cartao;
import entities.model.Conta;

import java.util.Scanner;

public class TelaCartao implements Tela {
    public static void exibirTelaCartao(){
        System.out.println("Você está na Tela de Extrato de Cartao de Crédito");
        TelaExtratoCartaoCredito.tratarInput(TelaExtratoCartaoCredito.pedirInput());
    }

    public static void tratarInput(int input) {
        Scanner scanner = new Scanner(System.in);
        Conta login;
        switch(input){
            case 1 -> {
                login = Tela.login();
                if (login != null) {
                    Cartao[] cartoes = login.getCartoes();
                    int cartao = -1;

                    for (int i = 0; i < cartoes.length; i++) {
                        if (cartoes[i] != null && cartoes[i].getTipo() == 2) {
                            cartao = i;
                            break;
                        }
                    }

                    if (cartao != -1) {
                        cartoes[cartao].exibirDadosCartao();
                        cartoes[cartao].exibirCompras();
                    } else {
                        System.out.println("Você não possui nenhum cartão de crédito");
                    }
                } else {
                    System.out.println("Login mal-sucedido");
                }
                exibirTelaCartao();
            }
            case 2 -> {
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
                exibirTelaCartao();
            }
            case 3 -> {
                login = Tela.login();
                if (login != null) {
                    if(login.getCartoes().length == 2){
                        System.err.println("Você não pode ADICIONAR mais CARTÕES só é possível ter no MÁXIMO 2 CARTÕES");
                    }else{
                        int tipoCartao;

                        System.out.println("Insira o tipo do cartão:");
                        System.out.println("[1] CRÉDITO");
                        System.out.println("[2] DÉBITO");
                        System.out.println("[3] CANCELAR");

                        tipoCartao = Integer.parseInt(scanner.nextLine());

                        if (tipoCartao < 1 || tipoCartao > 3){
                            System.out.println("Operação cancelada!");
                        }else{

                        }
                    }
                }else{
                    System.out.println("Login mal-sucedido");
                }
                exibirTelaCartao();
            }
            case 4 -> {
                login = Tela.login();
                if (login != null) {

                }else{
                    System.out.println("Login mal-sucedido");
                }
                exibirTelaCartao();
            }
            case 5 -> Tela.redirecionarParaTela(1);
            default -> {
                System.out.println("Opção inválida!");
                exibirTelaCartao();
            }
        }
    }

    public static int pedirInput() {
        System.out.println("[1] -> Exibir seu extrato do CARTÃO de CRÉDITO\n[2] -> Exibir seu extrato do CARTÃO de DÉBITO\n[3] -> ADICIONAR CARTÃO\n[4] -> EXCLUIR CARTÃO\n[5] -> Voltar para a Tela Principal");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }
}
