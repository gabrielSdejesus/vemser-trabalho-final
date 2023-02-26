package entities.view;

public class TelaPrincipal extends Tela {
    public static void exibirTelaPrincipal(){
        System.out.println("Você está na Tela Principal");
        TelaPrincipal.tratarInput();
    }

    public static void tratarInput() {
        int input = TelaPrincipal.pedirInput("[1] -> Ir para Tela De Compras\n" +
                "[2] -> Ir para Tela do Cartão\n" +
                "[3] -> Ir para Tela de Perfil\n" +
                "[4] -> Ir para Tela de Transferências\n" +
                "[5] -> Ir para Tela de Movimentações\n" +
                "[6] -> Ir para Tela do Administrador\n" +
                "[DIGITE QUALQUER COISA PARA ENCERRAR O PROGRAMA].");
        switch(input){
            case 1 -> Tela.redirecionarParaTela(2);
            case 2 -> Tela.redirecionarParaTela(3);
            case 3 -> Tela.redirecionarParaTela(4);
            case 4 -> Tela.redirecionarParaTela(5);
            case 5 -> Tela.redirecionarParaTela(6);
            case 6 -> Tela.redirecionarParaTela(7);
            default -> System.out.println("Programa encerrado!");
        }
    }
}
