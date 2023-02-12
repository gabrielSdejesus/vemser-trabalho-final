package entities.view;

import entities.interfaces.Tela;
import entities.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class TelaCompras  implements Tela {
    public static void exibirCompras(){
        System.out.println("\nVocê está na Tela de Compras");
        TelaCompras.tratarInput(TelaCompras.pedirInput());
    }

    public static void tratarInput(int input) {
        Scanner scanner = new Scanner(System.in);
        Conta login;
        switch(input){
            case 1 ->{
                login = Tela.login();
                if(login != null){
                    int i = 1;
                    for(Cartao cartao:login.getCartoes()){
                        if (cartao != null){
                            cartao.exibirCompras(i);
                            i++;
                        }
                    }
                }else{
                    System.err.println("Login mal-sucedido");
                }
                exibirCompras();
            }
            case 2 -> {
                login = Tela.login();
                if(login != null){
                    Cartao[] cartoes = login.getCartoes();
                    Cartao cartao = null;
                    ArrayList<Item> itens = new ArrayList<>();
                    String nomeItem = "";

                    double valorTotalAtual = 0;

                    System.out.println("Selecione o cartão para efetuar a compra:");
                    for(int i=0;i<cartoes.length;i++){
                        if(cartoes[i] != null){
                            System.out.printf("Cartão [%d] -> %s\n", (i+1), (cartoes[i].getTipo() == 1 ? "Débito":"Crédito"));
                        }
                    }

                    int i = Integer.parseInt(scanner.nextLine());
                    i--;
                    Item item = new Item();
                    if(i < login.getCartoes().length
                            && i >= 0
                                && cartoes[i] != null) {
                        cartao = cartoes[i];

                        do {
                            if(cartao.getTipo() == 1) {
                                System.out.printf("Saldo da conta: R$ %.2f", login.getSaldo());
                            }

                            if(cartao.getTipo() == 2){
                                System.out.printf("\nLimite disponível: R$ %.2f", ((CartaoDeCredito) cartao).limiteRestante());
                            }

                            System.out.printf("\nValor total da compra: R$ %.2f", valorTotalAtual);
                            System.out.println("\n\nInsira o nome do item a ser adicionado ou (digite SAIR para continuar):");
                            nomeItem = scanner.nextLine();
                            if (nomeItem.equalsIgnoreCase("SAIR")) {
                                break;
                            } else {
                                double valorItem, quantidadeItem;
                                System.out.println("Insira o valor do item:");
                                valorItem = Double.parseDouble(scanner.nextLine());
                                System.out.println("Insira a quantidade do item:");
                                quantidadeItem = Double.parseDouble(scanner.nextLine());
                                item = new Item(nomeItem, valorItem, quantidadeItem);
                                itens.add(item);
                                valorTotalAtual += item.returnPrecoItem();
                            }
                        } while (!nomeItem.equalsIgnoreCase("SAIR") && !nomeItem.isEmpty() && !nomeItem.isBlank());

                    if(!itens.isEmpty()
                            && !item.getNomeItem().isEmpty()
                                && !item.getNomeItem().isBlank()) {
                        String docVendedor;
                        System.out.println("Insira o documento do vendedor:");
                        docVendedor = scanner.nextLine();
                        if(cartao.getTipo() == 1) {
                            cartao.adicionarCompra(new Compra(docVendedor, new Date(), itens));
                        } else {
                            ((CartaoDeCredito)cartao).adicionarCompra(new Compra(docVendedor, new Date(), itens));
                        }
                    }
                        if(nomeItem.equalsIgnoreCase("SAIR")){
                            System.err.println("Você saiu da tela de adicionar compras!");
                            } else {
                                System.err.println("Os dados dos itens informados estão incorretos!");
                            }
                    } else {
                        System.err.println("Este número não representa nenhum cartão.");
                    }
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
