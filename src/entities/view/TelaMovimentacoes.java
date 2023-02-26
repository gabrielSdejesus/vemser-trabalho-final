package entities.view;

import entities.model.Conta;
import entities.service.ContaService;
import entities.service.Service;

public class TelaMovimentacoes extends Tela {
    public static void exibirTelaMovimentacoes(){
        Service.tempoParaExibir(500);
        System.out.println("Você está na Tela de Movimentações");
        TelaMovimentacoes.tratarInput();
    }

    public static void tratarInput() {
        int input = pedirInput("[1] -> Depositar\n" +
                "[2] -> Sacar\n" +
                "[3] -> Pagar uma conta externa\n" +
                "[0] -> Voltar para a Tela Principal");
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
