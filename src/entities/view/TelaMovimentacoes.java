package entities.view;

import entities.model.Conta;
import entities.service.ContaService;

public class TelaMovimentacoes extends Tela {
    public static void exibirTelaMovimentacoes(){
        System.out.println("\nVocê está na Tela de Movimentações");
        TelaMovimentacoes.tratarInput();
    }

    public static void tratarInput() {
        int input = pedirInput("[1] -> DEPOSITAR\n[2] -> SACAR\n[3] -> TRANSFERIR\n[4] -> PAGAR uma conta externa\n[5] -> Voltar para a Tela Principal");
        Conta login;
        switch(input){
            case 1 -> {
                login = Tela.login();
                if(login != null){
                    ContaService.depositar(login);
                }else{
                    System.err.println("Login mal-sucedido");
                }
                exibirTelaMovimentacoes();
            }
            case 2 -> {
                login = Tela.login();
                if(login != null){
                    ContaService.sacar(login);
                }else{
                    System.err.println("Login mal-sucedido");
                }
                exibirTelaMovimentacoes();
            }
            case 3 -> {
                login = Tela.login();
                if(login != null){
                    ContaService.transferir(login);
                }else{
                    System.err.println("Login mal-sucedido");
                }
                exibirTelaMovimentacoes();
            }
            case 4 -> {
                login = Tela.login();
                if(login != null){
                    ContaService.pagar(login);
                }else{
                    System.err.println("Login mal-sucedido");
                }
                exibirTelaMovimentacoes();
            }
            case 5 -> Tela.redirecionarParaTela(1);
            default -> {
                System.err.println("Opção inválida!");
                exibirTelaMovimentacoes();
            }
        }
    }
}
