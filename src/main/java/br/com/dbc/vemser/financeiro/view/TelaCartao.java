package br.com.dbc.vemser.financeiro.view;


import br.com.dbc.vemser.financeiro.model.Conta;
import br.com.dbc.vemser.financeiro.model.TipoCartao;
import br.com.dbc.vemser.financeiro.service.CartaoService;
import br.com.dbc.vemser.financeiro.service.Servico;

public class TelaCartao extends Tela {
    public static void exibirTelaCartao(){
        Servico.tempoParaExibir(500);
        System.out.println("Você está na Tela de Cartão");
        TelaCartao.tratarInput();
    }

    public static void tratarInput() {
        int input = TelaCartao.pedirInput("[1] -> Exibir informações do(s) cartão(s) de crédito\n" +
                "[2] -> Exibir informações do(s) cartão(s) de débito\n" +
                "[3] -> Adicionar cartão\n" +
                "[4] -> Excluir cartão\n" +
                "[0] -> Voltar para a Tela Principal");
        Conta login;
        CartaoService cartaoService = new CartaoService();
        switch(input){
            case 1 -> {
                login = Tela.login();
                if (login != null) {
                    cartaoService.exibirCartao(login, TipoCartao.CREDITO);
                } else {
                    System.err.println("Login mal-sucedido");
                }
                exibirTelaCartao();
            }
            case 2 -> {
                login = Tela.login();
                if(login != null){
                    cartaoService.exibirCartao(login, TipoCartao.DEBITO);
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
            case 0 -> Tela.redirecionarParaTela(1);
            default -> {
                System.err.println("Opção inválida!\n");
                exibirTelaCartao();
            }
        }
    }
}
