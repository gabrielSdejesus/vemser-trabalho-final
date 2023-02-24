package entities.service;

import entities.exception.BancoDeDadosException;
import entities.model.*;
import entities.repository.CompraRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CompraService extends Service{
    private CompraRepository compraRepository;

    public CompraService() {
        this.compraRepository = new CompraRepository();
    }

    public void exibirComprasCartao(Cartao cartao) {
        try{
            List<Compra> compras = new ArrayList<>(this.compraRepository.listarPorCartao(cartao.getNumeroCartao()));

            if(compras.size() != 0){
                for(Compra compra:compras){
                    System.out.println("Id da compra: "+compra.getIdCompra()+
                            "\nData da compra: "+compra.getData()+
                            "\nCartão usado na compra: "+compra.getCartao()+
                            "\nDocumento do vendedor: "+compra.getDocVendedor());
                }
            }else{
                System.out.println("Sem compras feitas nesse cartão");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void adicionarCompra(Conta conta){
        CartaoService cartaoService = new CartaoService();
        List<Cartao> cartoes = cartaoService.returnCartoes(conta);
        Cartao cartao;
        ArrayList<Item> itens = new ArrayList<>();
        String nomeItem;

        double valorTotalAtual = 0;

        System.out.println("Selecione o cartão para efetuar a compra:");
        for(int i=0;i<cartoes.size();i++){
            if(cartoes.get(i) != null){
                System.out.printf("Cartão [%d] -> %s\n", (i+1), (cartoes.get(i).getTipo() == TipoCartao.DEBITO ? "Débito":"Crédito"));
            }
        }

        int i = Integer.parseInt(SCANNER.nextLine());
        i--;
        Item item = new Item();
        if(i < cartoes.size()
                && i >= 0
                && cartoes.get(i) != null) {
            cartao = cartoes.get(i);

            do {
                if(cartao.getTipo() == TipoCartao.DEBITO) {
                    System.out.printf("Saldo da conta: R$ %.2f", conta.getSaldo());
                }

                if(cartao.getTipo() == TipoCartao.CREDITO){
                    System.out.printf("\nLimite disponível: R$ %.2f", ((CartaoDeCredito) cartao).getLimite());
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
                    double valorItem;
                    int quantidadeItem;
                    System.out.println("Insira o valor do item:");
                    valorItem = Double.parseDouble(SCANNER.nextLine());
                    System.out.println("Insira a quantidade do item:");
                    quantidadeItem = Integer.parseInt(SCANNER.nextLine());
                    if(quantidadeItem > 0 && valorItem > 0){
                        item = new Item();
                        item.setNome(nomeItem);
                        item.setQuantidade(quantidadeItem);
                        item.setValor(valorItem);
                        itens.add(item);
                        valorTotalAtual += item.getValor()*item.getQuantidade();
                    }else{
                        System.err.println("Item não adicionado!");
                        System.err.println("Valor/Quantidade do item inválidos!");
                    }
                }
            } while (!nomeItem.equalsIgnoreCase("SAIR") && !nomeItem.isEmpty() && !nomeItem.isBlank());

            if(!itens.isEmpty()
                    && !item.getNome().isEmpty()
                    && !item.getNome().isBlank()) {
                String docVendedor;
                System.out.println("Insira o documento do vendedor:");
                docVendedor = SCANNER.nextLine();

                ////Colocar a compra no BD
                Compra compra = new Compra();
                LocalDate localDate = LocalDate.now();
                compra.setDocVendedor(docVendedor);
                compra.setData(localDate);
                compra.setCartao(cartao);
                try{
                    compra = this.compraRepository.adicionar(compra);
                }catch (SQLException e){
                    e.printStackTrace();
                }
                ////

                /////Mandar todos os itens pro BD
                for(Item it:itens){
                    it.setCompra(compra);
                }
                try{
                    ItemService itemService = new ItemService();
                    itemService.adicionar(itens);
                }catch (SQLException e){
                    e.printStackTrace();
                }
                /////
            }
        } else {
            System.err.println("Este número não representa nenhum cartão.");
        }
    }
}
