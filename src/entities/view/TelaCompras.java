package entities.view;

import entities.interfaces.Tela;
import entities.model.Cartao;
import entities.model.Compra;
import entities.model.Conta;
import entities.model.Item;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class TelaCompras  implements Tela {
    public static void exibirCompras(){
        System.out.println("Você está na Tela de Compras");
        TelaCompras.tratarInput(TelaCompras.pedirInput());
    }

    public static void tratarInput(int input) {
        switch(input){
            case 1 ->{
                Conta login = Tela.login();
                if(login != null){
                    for(Cartao cartao:login.getCartoes()){
                        cartao.exibirCompras();
                    }
                }else{
                    System.out.println("Login mal-sucedido");
                }
                exibirCompras();
            }
            case 2 -> {
                Conta login = Tela.login();
                if(login != null){
                    Scanner scanner = new Scanner(System.in);
                    Cartao[] cartoes = login.getCartoes();
                    Cartao cartao;
                    ArrayList<Item> itens = new ArrayList<>();
                    String nomeItem = "";

                    double valorTotalAtual = 0;

                    System.out.println("Selecione o cartão para efetuar a compra:");
                    for(int i=0;i<cartoes.length;i++){
                        if(cartoes[i] != null){
                            System.out.printf("Cartão [%d] -> %s", (i+1), (cartoes[i].getTipo() == 1 ? "Débito":"Crédito"));
                        }
                    }
                    cartao = cartoes[Integer.parseInt(scanner.nextLine())-1];


                    while(!nomeItem.equalsIgnoreCase("ENCERRAR COMPRA")){
                        System.out.printf("Saldo da conta: R$ %.2f", login.getSaldo());
                        System.out.printf("Valor total da compra: R$ %.2f", valorTotalAtual);
                        System.out.println("Digite [ENCERRAR COMPRA] para encerrar a adição de itens");
                        System.out.println("Insira o nome do item a ser adicionado:");
                        nomeItem = scanner.nextLine();
                        if(nomeItem.equalsIgnoreCase("ENCERRAR COMPRA")){
                            break;
                        }else{
                            double valorItem, quantidadeItem;
                            System.out.println("Insira o valor do item:");
                            valorItem = Double.parseDouble(scanner.nextLine());
                            System.out.println("Insira a quantidade do item:");
                            quantidadeItem = Double.parseDouble(scanner.nextLine());
                            Item item = new Item(nomeItem, valorItem, quantidadeItem);
                            itens.add(item);
                            valorTotalAtual += item.returnPrecoItem();
                        }
                    }

                    String docVendedor;
                    System.out.println("Insira o documento do vendedor:");
                    docVendedor = scanner.nextLine();

                    cartao.adicionarCompra(new Compra(docVendedor, new Date(), itens));
                }else {
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
        System.out.println("[1] -> Exibir compras da sua conta\n[2] -> Adicionar uma compra\n[3]-> Voltar para a Tela Principal");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }
}
