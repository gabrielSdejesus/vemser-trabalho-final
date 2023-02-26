package entities.view;

import entities.model.*;
import entities.service.CartaoService;
import entities.service.CompraService;
import entities.service.Service;

import java.util.List;

public class TelaCompras extends Tela {
    public static void exibirCompras(){
        Service.tempoParaExibir(500);
        System.out.println("Você está na Tela de Compras");
        TelaCompras.tratarInput();
    }

    public static void tratarInput() {
        int input = pedirInput("[1] -> Exibir compras da sua conta\n" +
                "[2] -> Adicionar uma compra\n" +
                "[3] -> Voltar para a Tela Principal");
        CompraService compraService = new CompraService();
        Conta login;
        switch(input){
            case 1 ->{
                login = Tela.login();
                if(login != null){
                    CartaoService cartaoService = new CartaoService();
                    List<Cartao> cartoes = cartaoService.returnCartoes(login);
                    if (cartoes != null) {
                        for(Cartao cartao: cartoes) {
                            compraService.exibirComprasCartao(cartao);
                        }
                    } else {
                        System.out.println("\n\tNão há compras nesta conta.");
                    }
                }else{
                    System.err.println("Login mal-sucedido\n");
                }
                exibirCompras();
            }
            case 2 -> {
                login = Tela.login();
                if(login != null){
                    compraService.adicionarCompra(login);
                } else {
                    System.out.println("Login mal-sucedido\n");
                }
                exibirCompras();
            }
            case 3 -> Tela.redirecionarParaTela(1);
            default -> {
                System.err.println("Opção inválida!\n");
                exibirCompras();
            }
        }
    }
}
