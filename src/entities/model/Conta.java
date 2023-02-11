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
    private BancoDeDados bancoDeDados = new BancoDeDados();

    public Conta (Cliente cliente, double saldoInicial) {

        int numero = new Random().nextInt(1000, 5000);
        while (bancoDeDados.consultarNumeroDeConta(numero)){
            numero++;
        }

        if(true && saldoInicial >= 0
                    && !bancoDeDados.consultarExistenciaPorCPF(cliente)){
            this.cliente = cliente;
            this.numero = numero;
            this.agencia = new Random().nextInt(1000,2000);
            this.saldo = saldoInicial;
            this.senha = cliente.getSenha();
            bancoDeDados.adicionarConta(this);
        }
    }

    public boolean sacar (double valor, String senha){

        if(valor > 0 && valor <= this.saldo + CHEQUE_ESPECIAL && verificarSenha(senha)){
            this.saldo -= valor;
            return true;
        }
        return false;    }

    public boolean depositar(double valor, String senha){

        if(valor > 0 && verificarSenha(senha)){
            this.saldo += valor;
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
            return true;
        }
        return false;
    }

    public boolean adicionarCartao(Cartao cartao, String senha){

        if(verificarSenha(senha) && cartao != null){
            for(int i = 0; i < this.cartoes.length; i++){
                if(this.cartoes[i] == null){
                    this.cartoes[i] = cartao;
                    bancoDeDados.alterarDadosDaConta(this);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean removerCartao(int indice, String senha){

        if((indice == 0 || indice == 1)
                && verificarSenha(senha)) {
            this.cartoes[indice] = new Cartao();
            bancoDeDados.alterarDadosDaConta(this);
            return true;
        };
        return false;
    }

    public boolean alterarSenha(String senhaAntiga, String novaSenha){

        if(verificarSenha(senhaAntiga)){
            this.senha = novaSenha;
            bancoDeDados.alterarDadosDaConta(this);
            return true;
        }
        return false;
    }

    private boolean verificarSenha(String senha){

        if(this.senha.equals(senha)){
            return true;
        }
        return false;
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

    @Override
    public void exibir() {

    }
}
