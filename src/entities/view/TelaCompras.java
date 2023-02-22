package entities.view;

import entities.model.*;
import entities.service.CompraService;
import java.util.Scanner;

public class TelaCompras extends Tela {
    public static void exibirCompras(){
        System.out.println("\nVocê está na Tela de Compras");
        TelaCompras.tratarInput(TelaCompras.pedirInput());
    }

    public static void tratarInput(int input) {
        Conta login;
        switch(input){
            case 1 ->{
                login = Tela.login();
                if(login != null){
                    CompraService.exibirCompras(login);
                }else{
                    System.err.println("Login mal-sucedido");
                }
                exibirCompras();
            }
            case 2 -> {
                login = Tela.login();
                if(login != null){
                    CompraService.adicionarCompra(login);
                } else {
                    System.out.println("Login mal-sucedido");
                }
                exibirCompras();
            }
            case 3 -> Tela.redirecionarParaTela(1);
            default -> {
                System.out.println("Opção inválida!");
                exibirCompras();
            }
        }
    }

    public static int pedirInput() {
        System.out.println("[1] -> Exibir compras da sua conta\n[2] -> Adicionar uma compra\n[3] -> Voltar para a Tela Principal");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }
}
