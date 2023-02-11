package entities.model;

import entities.controller.BancoDeDados;
import entities.interfaces.Exibicao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class Cartao {
    private LocalDate dataExpedicao;
    private int codigoSeguranca;
    private int numero;
    private String senha;
    private LocalDate vencimento;
    private Conta conta;
    private List<Compra> compras = new ArrayList<>();

    public Cartao(){}

    public Cartao(Conta conta) {

        int numero = new Random().nextInt(10000000, 99999999);
        while (BancoDeDados.consultarNumeroDoCartao(numero)){
            numero++;
        }

        this.dataExpedicao = LocalDate.now();
        this.codigoSeguranca = new Random().nextInt(100,999);
        this.numero = numero;
        this.senha = conta.getSenha();
        this.vencimento = LocalDate.now().plusYears(4);
        this.conta = conta;
    }

    public void adicionarCompra(Compra compra) {
        if(pagarComCartao(compra.returnValorTotal(), this.senha, compra)){
            compras.add(compra);
        }else{
            System.err.println("Compra não realizada, saldo indisponível!");
        }
    }

    public boolean pagarComCartao(double valor, String senha, Compra compra){
        if(conta.getSaldo() >= valor
                && valor > 0
                    && compra.returnValorTotal() == valor) {
            conta.sacar(valor, senha);
            return true;
        }
        return  false;
    }

    public void exibirDadosCartao() {
        System.out.println("\t\tData de expedição: "+dataExpedicao);
        System.out.println("\t\tCódigo de Segurança: "+codigoSeguranca);
        System.out.println("\t\tNúmero: "+numero);
        System.out.println("\t\tVencimento: "+vencimento);
        System.out.println("\t\tTipo: " + (getTipo() == 1? "Débito": "Crédito"));
    }

    public void alterarSenhaDoCartao(String novaSenha, String senhaAntiga) {
        conta.alterarSenha(senhaAntiga,novaSenha);
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public Conta getConta() {
        return conta;
    }

    public void exibirCompras() {
        System.out.println("Exibindo compras do cartão ["+ (conta.getCartoes()[0] == this? "1": "2") +"] de "+(getTipo() == 1? "Débito": "Crédito"));
        for(Compra compra: compras) {
            compra.exibir();
        }
    }

    public int getTipo(){
        return 0;
    }
}
