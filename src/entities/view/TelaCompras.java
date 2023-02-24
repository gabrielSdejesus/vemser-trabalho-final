package entities.view;

import entities.model.*;
import entities.service.CartaoService;
import entities.service.CompraService;

public class TelaCompras extends Tela {
    public static void exibirCompras(){
        System.out.println("\nVocê está na Tela de Compras");
        TelaCompras.tratarInput();
    }

    public static void tratarInput() {
        int input = pedirInput("[1] -> Exibir compras da sua conta\n[2] -> Adicionar uma compra\n[3] -> Voltar para a Tela Principal");
        CompraService compraService = new CompraService();
        Conta login;
        switch(input){
            case 1 ->{
                login = Tela.login();
                if(login != null){
                    CartaoService cartaoService = new CartaoService();
                    for(Cartao cartao: cartaoService.returnCartoes(login)){
                        compraService.exibirComprasCartao(cartao);
                    }
                }else{
                    System.err.println("Login mal-sucedido");
                }
                exibirCompras();
            }
            case 2 -> {
                login = Tela.login();
                if(login != null){
                    compraService.adicionarCompra(login);
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
}
