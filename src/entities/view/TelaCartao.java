package entities.view;

import entities.model.*;
import entities.service.CartaoService;
import entities.service.Service;

public class TelaCartao extends Tela {
    public static void exibirTelaCartao(){
        Service.tempoParaExibir(500);
        System.out.println("Você está na Tela de Cartão");
        TelaCartao.tratarInput();
    }

    public static void tratarInput() {
        int input = TelaCartao.pedirInput("[1] -> Exibir informações do(s) cartão(s) de crédito\n" +
                "[2] -> Exibir informações do(s) cartão(s) de débito\n" +
                "[3] -> Adicionar cartão\n" +
                "[4] -> Excluir cartão\n" +
                "[5] -> Voltar para a Tela Principal");
        Conta login;
        CartaoService cartaoService = new CartaoService();
        switch(input){
            case 1 -> {
                login = Tela.login();
                if (login != null) {
                    cartaoService.exibirCartao(login, TipoCartao.CREDITO);
                } else {
                    System.err.println("Login mal-sucedido\n");
                }
                exibirTelaCartao();
            }
            case 2 -> {
                login = Tela.login();
                if(login != null){
                    cartaoService.exibirCartao(login, TipoCartao.DEBITO);
                }else {
                    System.err.println("Login mal-sucedido\n");
                }
                exibirTelaCartao();
            }
            case 3 -> {
                login = Tela.login();
                if (login != null) {
                    cartaoService.cadastrarCartao(login, null);
                }else{
                    System.err.println("Login mal-sucedido\n");
                }
                exibirTelaCartao();
            }
            case 4 -> {
                login = Tela.login();
                if (login != null) {
                    cartaoService.deletarCartao(login);
                }else{
                    System.err.println("Login mal-sucedido\n");
                }
                exibirTelaCartao();
            }
            case 5 -> Tela.redirecionarParaTela(1);
            default -> {
                System.out.println("Opção inválida!\n");
                exibirTelaCartao();
            }
        }
    }
}
