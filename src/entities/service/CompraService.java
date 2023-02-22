package entities.service;

import entities.model.*;

import java.util.ArrayList;
import java.util.Date;

public class CompraService extends Service{
    public static void exibirCompras(Conta conta){
        int i = 1;
        for(Cartao cartao:conta.getCartoes()){
            if (cartao != null){
                cartao.exibirCompras(i);
                i++;
            }
        }
    }

    public static void adicionarCompra(Conta conta){
        Cartao[] cartoes = conta.getCartoes();
        Cartao cartao;
        ArrayList<Item> itens = new ArrayList<>();
        String nomeItem;

        double valorTotalAtual = 0;

        System.out.println("Selecione o cartão para efetuar a compra:");
        for(int i=0;i<cartoes.length;i++){
            if(cartoes[i] != null){
                System.out.printf("Cartão [%d] -> %s\n", (i+1), (cartoes[i].getTipo() == 1 ? "Débito":"Crédito"));
            }
        }

        int i = Integer.parseInt(SCANNER.nextLine());
        i--;
        Item item = new Item();
        if(i < conta.getCartoes().length
                && i >= 0
                && cartoes[i] != null) {
            cartao = cartoes[i];

            do {
                if(cartao.getTipo() == 1) {
                    System.out.printf("Saldo da conta: R$ %.2f", conta.getSaldo());
                }

                if(cartao.getTipo() == 2){
                    System.out.printf("\nLimite disponível: R$ %.2f", ((CartaoDeCredito) cartao).limiteRestante());
                }

                System.out.printf("\nValor total da compra: R$ %.2f", valorTotalAtual);
                System.out.println("\n\nInsira o nome do item a ser adicionado ou (digite SAIR para continuar):");
                nomeItem = SCANNER.nextLine();
                if (nomeItem.equalsIgnoreCase("SAIR")) {
                    if(itens.size() > 0){
                        System.out.println("\nCompra Adicionada com sucesso!");
                    }else{
                        System.out.println("\nCompra não realizada!");
                    }
                    break;
                } else {
                    double valorItem, quantidadeItem;
                    System.out.println("Insira o valor do item:");
                    valorItem = Double.parseDouble(SCANNER.nextLine());
                    System.out.println("Insira a quantidade do item:");
                    quantidadeItem = Double.parseDouble(SCANNER.nextLine());
                    if(quantidadeItem > 0 && valorItem > 0){
                        item = new Item(nomeItem, valorItem, quantidadeItem);
                        itens.add(item);
                        valorTotalAtual += item.returnPrecoItem();
                    }else{
                        System.err.println("Item não adicionado!");
                        System.err.println("Valor/Quantidade do item inválidos!");
                    }
                }
            } while (!nomeItem.equalsIgnoreCase("SAIR") && !nomeItem.isEmpty() && !nomeItem.isBlank());

            if(!itens.isEmpty()
                    && !item.getNomeItem().isEmpty()
                    && !item.getNomeItem().isBlank()) {
                String docVendedor;
                System.out.println("Insira o documento do vendedor:");
                docVendedor = SCANNER.nextLine();
                ////
                cartao.adicionarCompra(new Compra(docVendedor, new Date(), itens));
                System.out.println("INFOS CARTAO: ");
                cartao.exibirDadosCartao();
            }
        } else {
            System.err.println("Este número não representa nenhum cartão.");
        }
    }
}
