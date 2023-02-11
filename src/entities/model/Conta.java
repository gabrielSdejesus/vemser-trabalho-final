package entities.model;

import entities.controller.BancoDeDados;
import entities.interfaces.Exibicao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Conta implements Exibicao {
    private Cliente cliente;
    private String senha;
    private int numero;
    private int agencia;
    private double saldo;
    private static final double CHEQUE_ESPECIAL = 200;
    private Cartao[] cartoes = new Cartao[2];
    private List<Transferencia> transferencias = new ArrayList<>();

    public Conta (Cliente cliente, double saldoInicial) {
        if(saldoInicial >= 0
                && !BancoDeDados.consultarExistenciaPorCPF(cliente)){

            int numero = new Random().nextInt(1000, 5000);
            while (BancoDeDados.consultarNumeroDaConta(numero)){
                numero++;
            }

            this.cliente = cliente;
            this.numero = numero;
            this.agencia = new Random().nextInt(1000,2000);
            this.saldo = saldoInicial;
            this.senha = cliente.getSenha();
            this.cartoes[0] = new CartaoDeDebito(this);
            BancoDeDados.adicionarConta(this);
        }
    }

    public boolean sacar (double valor, String senha){
        if(valor > 0 && valor <= this.saldo + CHEQUE_ESPECIAL && verificarSenha(senha)){
            this.saldo -= valor;
            BancoDeDados.alterarDadosDaConta(this);
            return true;
        }
        return false;    }

    public boolean depositar(double valor, String senha){
        if(valor > 0 && verificarSenha(senha)){
            this.saldo += valor;
            BancoDeDados.alterarDadosDaConta(this);
            return true;
        }
        return false;
    }

    public boolean transferir(Conta conta, double valor, String senha){
        if(valor > 0 && valor >= this.saldo
                && verificarSenha(senha)){
            this.saldo -= valor;
            conta.saldo += conta.getSaldo() + valor;
            this.transferencias.add(new Transferencia(conta,this,valor));
            BancoDeDados.alterarDadosDaConta(this);
            return true;
        }
        return false;
    }

    public boolean adicionarCartao(Cartao cartao, String senha){
        if(verificarSenha(senha) && cartao != null){
            for(int i = 0; i < this.cartoes.length; i++){
                if(this.cartoes[i] == null){
                    this.cartoes[i] = cartao;
                    BancoDeDados.alterarDadosDaConta(this);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean removerCartao(int indice, String senha){
        if((indice == 0 || indice == 1)
                && verificarSenha(senha)) {
            this.cartoes[indice] = null;
            BancoDeDados.alterarDadosDaConta(this);
            return true;
        };
        return false;
    }

    public boolean alterarSenha(String senhaAntiga, String novaSenha){
        if(verificarSenha(senhaAntiga)){
            this.senha = novaSenha;
            BancoDeDados.alterarDadosDaConta(this);
            return true;
        }
        return false;
    }

    public boolean verificarSenha(String senha){
        return this.senha.equals(senha);
    }

    public double getSaldo() {
        return saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public int getNumero() {
        return numero;
    }

    public String getSenha() {
        return senha;
    }

    @Override
    public void exibir() {
        System.out.printf("\tConta: %d\n\tCliente: %s\n\tAgência: %d\n\tSaldo: R%.2f\n\tCheque especial: R$ %.2f", numero, cliente.getNome(), agencia, saldo, CHEQUE_ESPECIAL);
        System.out.println("\tCartões:");
        for(Cartao cartao:cartoes){
            cartao.exibirDadosCartao();
        }
    }

    public Cartao[] getCartoes() {
        return cartoes;
    }

    public void exibirTransferencias() {
        System.out.println("Exibindo todas as transferências da conta ["+this.numero+"]:");
        for(Transferencia transferencia: transferencias){
            transferencia.exibir();
        }
    }
}
