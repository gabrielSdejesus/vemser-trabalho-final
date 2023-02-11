package entities.model;

import java.util.Date;
import java.util.List;

public class Cartao {
    private Date dataExpedicao;
    private int codigoSeguranca;
    private int numero;
    private int senha;
    private Date vencimento;
    private Conta conta;
    private List<Compra> compras;

    public Cartao(){};

    public Cartao(Date dataExpedicao, int codigoSeguranca, int numero, int senha, Date vencimento, Conta conta, List<Compra> compras) {
        this.dataExpedicao = dataExpedicao;
        this.codigoSeguranca = codigoSeguranca;
        this.numero = numero;
        this.senha = senha;
        this.vencimento = vencimento;
        this.conta = conta;
        this.compras = compras;
    }

    public void adicionarCompra(Compra compra) {
        compras.add(compra);
    }

    public void segundaViaCartao(int senha, int numero, Date vencimento, Date dataExpedicao, int codigoSeguranca) {

    }

    public void exibirDadosCartao() {}

    public void alterarSenha(int novaSenha) {
        this.senha = novaSenha;
    }

}
