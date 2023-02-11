package entities.interfaces;

import entities.view.*;

public interface Tela {
    void tratarInput(int input);
    int pedirInput();
    default void redirecionarParaTela(int tela){
        switch(tela){
            case 1 -> {
                TelaPrincipal.exibirTelaPrincipal();
            }
            case 2 -> {
                TelaCompras.exibirCompras();
            }
            case 3 -> {
                TelaExtratoCartaoCredito.exibirTelaExtratoCartaoCredito();
            }
            case 4 -> {
                TelaExtratoCartaoDebito.exibirTelaExtratoCartaoDebito();
            }
            case 5 -> {
                TelaPerfil.exibirTelaPerfil();
            }
            case 6 -> {
                TelaTransferencias.exibirTransferencias();
            }
            default -> {
                System.err.println("Número da tela incorreta, erro na linha 29 da Inteface Tela");
            }
        }
    };
}
