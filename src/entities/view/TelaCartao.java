package entities.view;

import entities.interfaces.Tela;
import entities.model.Cartao;
import entities.model.CartaoDeCredito;
import entities.model.CartaoDeDebito;
import entities.model.Conta;

import java.util.Scanner;

public class TelaCartao implements Tela {
    public static void exibirTelaCartao(){
        System.out.println("\nVocê está na Tela de Cartão");
        TelaCartao.tratarInput(TelaCartao.pedirInput());
    }

    public static void tratarInput(int input) {
        Scanner scanner = new Scanner(System.in);
        Conta login;
        switch(input){
            case 1 -> {
                login = Tela.login();
                if (login != null) {
                    Cartao[] cartoes = login.getCartoes();
                    int cartao = -1;

                    for (int i = 0; i < cartoes.length; i++) {
                        if (cartoes[i] != null && cartoes[i].getTipo() == 2) {
                            cartao = i;
                            System.out.println("\t\nExibindo dados do cartão ["+(i+1)+"]:");
                            cartoes[cartao].exibirDadosCartao();
                            cartoes[cartao].exibirCompras((i+1));
                        }
                    }
                    if (cartao == -1) {
                        System.out.println("\tVocê não possui nenhum cartão de crédito\n");
                    }
                } else {
                    System.err.println("Login mal-sucedido");
                }
                exibirTelaCartao();
            }
            case 2 -> {
                login = Tela.login();
                if(login != null){
                    Cartao[] cartoes = login.getCartoes();
                    int cartao = -1;

                    for (int i = 0; i < cartoes.length; i++) {
                        if (cartoes[i] != null && cartoes[i].getTipo() == 1) {
                            cartao = i;
                            System.out.println("\tExibindo dados do cartão ["+(i+1)+"]:");
                            cartoes[cartao].exibirDadosCartao();
                            cartoes[cartao].exibirCompras((i));
                        }
                    }
                    if (cartao == -1) {
                        System.out.println("\tVocê não possui nenhum cartão de débito");
                    }
                }else {
                    System.err.println("Login mal-sucedido");
                }
                exibirTelaCartao();
            }
            case 3 -> {
                login = Tela.login();
                if (login != null) {
                    int contador = 0;
                    for(Cartao cartao: login.getCartoes()){
                        if(cartao != null){
                            contador++;
                        }
                    }
                    if(contador == 2){
                        System.err.println("Você não pode ADICIONAR mais CARTÕES, só é possível ter no MÁXIMO 2 CARTÕES");
                    }else{
                        int tipoCartao;

                        System.out.println("Insira o tipo do cartão:");
                        System.out.println("[1] CRÉDITO");
                        System.out.println("[2] DÉBITO");
                        System.out.println("[3] CANCELAR");

                        tipoCartao = Integer.parseInt(scanner.nextLine());

                        String senha;

                        if (tipoCartao < 1 || tipoCartao >= 3){
                            System.out.println("Operação cancelada!");
                        }else{
                            System.out.println("Digite novamente sua senha:");
                            senha = scanner.nextLine();
                            switch(tipoCartao){
                                case 1 -> {
                                    CartaoDeCredito cartaoDeCredito = new CartaoDeCredito(login);
                                    if(login.adicionarCartao(cartaoDeCredito, senha)){
                                        System.out.println("Novo CARTÃO de CRÉDITO adicionado com sucesso!");
                                        System.out.println("\tDados do CARTÃO:");
                                        cartaoDeCredito.exibirDadosCartao();
                                    }else{
                                        System.err.println("Senha incorreta!");
                                    }
                                }
                                case 2 -> {
                                    CartaoDeDebito cartaoDeDebito = new CartaoDeDebito(login);
                                    if(login.adicionarCartao(cartaoDeDebito, senha)){
                                        System.out.println("Novo CARTÃO de DÉBITO adicionado com sucesso!");
                                        System.out.println("\tDados do CARTÃO:");
                                        cartaoDeDebito.exibirDadosCartao();
                                    }else{
                                        System.err.println("Senha incorreta!");
                                    }
                                }
                                default -> System.err.println("Erro bizarro!");
                            }
                        }
                    }
                }else{
                    System.err.println("Login mal-sucedido");
                }
                exibirTelaCartao();
            }
            case 4 -> {
                login = Tela.login();
                if (login != null) {
                    int contador = 0;
                    for(Cartao cartao: login.getCartoes()){
                        if(cartao != null){
                            contador++;
                        }
                    }
                    if(contador == 1){
                        System.err.println("Você não pode REMOVER mais CARTÕES, é NECESSÁRIO ter no MÍNIMO 1 CARTÃO");
                    }else{
                        Cartao[] cartoes = login.getCartoes();
                        int cartao;

                        System.out.println("Selecione o CARTÃO para REMOVER:");
                        for(int i=0;i<cartoes.length;i++){
                            if(cartoes[i] != null){
                                System.out.printf("Cartão [%d] -> %s\n", (i+1), (cartoes[i].getTipo() == 1 ? "Débito":"Crédito"));
                            }
                        }
                        cartao = Integer.parseInt(scanner.nextLine())-1;

                        String senha;
                        System.out.println("Digite novamente sua senha:");
                        senha = scanner.nextLine();

                        if(login.removerCartao(cartao, senha)){
                            System.err.println("CARTÃO removido com sucesso!");
                        }else{
                            System.err.println("Senha incorreta!");
                        }
                    }
                }else{
                    System.err.println("Login mal-sucedido");
                }
                exibirTelaCartao();
            }
            case 5 -> Tela.redirecionarParaTela(1);
            default -> {
                System.out.println("Opção inválida!");
                exibirTelaCartao();
            }
        }
    }

    public static int pedirInput() {
        System.out.println("[1] -> Exibir seu extrato do CARTÃO de CRÉDITO\n[2] -> Exibir seu extrato do CARTÃO de DÉBITO\n[3] -> ADICIONAR CARTÃO\n[4] -> EXCLUIR CARTÃO\n[5] -> Voltar para a Tela Principal");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }
}
