package entities.view;

import entities.model.*;
import entities.service.CartaoService;

public class TelaCartao extends Tela {
    public static void exibirTelaCartao(){
        System.out.println("Você está na Tela de Cartão");
        TelaCartao.tratarInput();
    }

    public static void tratarInput() {
        int input = TelaCartao.pedirInput("[1] -> Exibir seu extrato do CARTÃO de CRÉDITO\n[2] -> Exibir seu extrato do CARTÃO de DÉBITO\n[3] -> ADICIONAR CARTÃO\n[4] -> EXCLUIR CARTÃO\n[5] -> Voltar para a Tela Principal");
        Conta login;
        CartaoService cartaoService = new CartaoService();
        switch(input){
            case 1 -> {
                login = Tela.login();
                if (login != null) {
                    cartaoService.exibirExtrato(login, TipoCartao.CREDITO);
                } else {
                    System.err.println("Login mal-sucedido");
                }
                exibirTelaCartao();
            }
            case 2 -> {
                login = Tela.login();
                if(login != null){
                    cartaoService.exibirExtrato(login, TipoCartao.DEBITO);
                }else {
                    System.err.println("Login mal-sucedido");
                }
                exibirTelaCartao();
            }
            case 3 -> {
                login = Tela.login();
                if (login != null) {
                    cartaoService.cadastrarCartao(login, null);
                }else{
                    System.err.println("Login mal-sucedido");
                }
                exibirTelaCartao();
            }
            case 4 -> {
                login = Tela.login();
                if (login != null) {
                    cartaoService.deletarCartao(login);
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
}
