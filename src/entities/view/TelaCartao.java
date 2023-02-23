package entities.view;

import entities.model.Conta;
import entities.service.CartaoService;


public class TelaCartao extends Tela {
    public static void exibirTelaCartao(){
        System.out.println("\nVocê está na Tela de Cartão");
        TelaCartao.tratarInput();
    }

    public static void tratarInput() {
        int input = pedirInput("[1] -> Cadastrar um novo CLIENTE com CONTA\n[2] -> Deletar CLIENTE\n[3] -> Deletar CONTA\n[4] -> Voltar para a Tela Principal");
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
}
