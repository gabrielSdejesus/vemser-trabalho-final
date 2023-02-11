package entities.interfaces;

import entities.view.*;

public interface Tela {
    static void tratarInput(int input){
        // cada classe que implementa essa inteface deve refazer esse método da sua forma
    }
    static int pedirInput(){
        // cada classe que implementa essa inteface deve refazer esse método da sua forma
        return 0;
    }
    static void redirecionarParaTela(int tela){
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
