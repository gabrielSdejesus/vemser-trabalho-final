package entities.view;

import entities.model.Conta;
import entities.service.CartaoService;

import java.util.Scanner;

public class TelaCartao extends Tela {
    public static void exibirTelaCartao(){
        System.out.println("\nVocê está na Tela de Cartão");
        TelaCartao.tratarInput(TelaCartao.pedirInput());
    }

    public static void tratarInput(int input) {
        Scanner scanner = new Scanner(System.in);
        Conta login;
        switch(input){
            case 1 -> {
                login = Tela.login();
                if (login != null) {
                    CartaoService.exibirExtrato(login, 2);
                } else {
                    System.err.println("Login mal-sucedido");
                }
                exibirTelaCartao();
            }
            case 2 -> {
                login = Tela.login();
                if(login != null){
                    CartaoService.exibirExtrato(login, 1);
                }else {
                    System.err.println("Login mal-sucedido");
                }
                exibirTelaCartao();
            }
            case 3 -> {
                login = Tela.login();
                if (login != null) {
                    CartaoService.cadastrarCartao(login);
                }else{
                    System.err.println("Login mal-sucedido");
                }
                exibirTelaCartao();
            }
            case 4 -> {
                login = Tela.login();
                if (login != null) {
                    CartaoService.deletarCartao(login);
                }else{
                    System.err.println("Login mal-sucedido");
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
