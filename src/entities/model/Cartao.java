package entities.model;

import entities.controller.BancoDeDados;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Cartao {
    private LocalDate dataExpedicao;
    private int codigoSeguranca;
    private int numero;
    private String senha;
    private LocalDate vencimento;
    private Conta conta;
    private List<Compra> compras;

    public Cartao(){};

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
        compras.add(compra);
    }

    public void segundaViaCartao(int senha, int numero, Date vencimento, Date dataExpedicao, int codigoSeguranca) {}

    public void exibirDadosCartao() {}

    public void alterarSenha(String novaSenha) {
        this.senha = novaSenha;
    }

    public Conta getConta() {
        return conta;
    }
}
