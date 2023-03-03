package br.com.dbc.vemser.financeiro.view;


import br.com.dbc.vemser.financeiro.model.Conta;
import br.com.dbc.vemser.financeiro.service.ContaService;
import br.com.dbc.vemser.financeiro.service.Servico;

public class TelaMovimentacoes extends Tela {
    public static void exibirTelaMovimentacoes(){
        Servico.tempoParaExibir(500);
        System.out.println("Você está na Tela de Movimentações");
        TelaMovimentacoes.tratarInput();
    }

    public static void tratarInput() {
        int input = pedirInput("""
                [1] -> Depositar
                [2] -> Sacar
                [3] -> Pagar uma conta externa
                [0] -> Voltar para a Tela Principal""");
        ContaService contaService = new ContaService();
        Conta login;
        switch(input){
            case 1 -> {
                login = Tela.login();
                if(login != null){
                    contaService.depositar(login);
                }else{
                    System.err.println("\nLogin mal-sucedido");
                }
                exibirTelaMovimentacoes();
            }
            case 2 -> {
                login = Tela.login();
                if(login != null){
                    contaService.sacar(login);
                }else{
                    System.err.println("\nLogin mal-sucedido");
                }
                exibirTelaMovimentacoes();
            }
            case 3 -> {
                login = Tela.login();
                if(login != null){
                    contaService.pagar(login);
                }else{
                    System.err.println("\nLogin mal-sucedido");
                }
                exibirTelaMovimentacoes();
            }
            case 0 -> Tela.redirecionarParaTela(1);
            default -> {
                System.err.println("Opção inválida!\n");
                exibirTelaMovimentacoes();
            }
        }
    }
}
