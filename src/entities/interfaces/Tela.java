package entities.interfaces;

import entities.view.*;

public interface Tela {
    void tratarInput(int input);
    int pedirInput();
    default void redirecionarParaTela(int tela){
        switch(tela){
            case 1 -> {
                new TelaPrincipal().exibirTelaPrincipal();
            }
            case 2 -> {
                new TelaCompras().exibirCompras();
            }
            case 3 -> {
                new TelaExtratoCartaoCredito().exibirTelaExtratoCartaoCredito();
            }
            case 4 -> {
                new TelaExtratoCartaoDebito().exibirTelaExtratoCartaoDebito();
            }
            case 5 -> {
                new TelaPerfil().exibirTelaPerfil();
            }
            case 6 -> {
                new TelaTransferencias().exibirTransferencias();
            }
            default -> {
                System.err.println("Número da tela incorreta, erro na linha 29 da Inteface Tela");
            }
        }
    };
}
