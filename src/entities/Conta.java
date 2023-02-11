package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Conta {

    private Cliente cliente;
    private int senha;
    private int numero;
    private int agencia;
    private double saldo;
    private static final double CHEQUE_ESPECIAL = 200;
    private Cartao[] cartoes = new Cartao[2];
    private List<Transferencia> transferencias = new ArrayList<>();

    public Conta(){};

    public Conta (Cliente cliente, double saldoInicial){
        //verificar se há o CPF dentro do banco de dados;
        //verificar se o numero gerado já existe no banco de dados;
        if(true && saldoInicial >= 0){
            this.cliente = cliente;
            this.numero = new Random().nextInt(1000, 5000);
            this.agencia = new Random().nextInt(1000,2000);
            this.saldo = saldoInicial;
            BancoDeDados bancoDeDados = new BancoDeDados();
            bancoDeDados.adicionarConta(this);
        }
    }

    public boolean sacar (double valor){

        if(valor > 0 && valor <= this.saldo + CHEQUE_ESPECIAL){
            this.saldo -= valor;
            return true;
        }
        return false;
    }

    public boolean depositar(double valor){

        if(valor > 0){
            this.saldo += valor;
            return true;
        }
        return false;
    }

    public boolean transferir(Conta conta, double valor,int senha){

        if(valor > 0 && valor >= this.saldo
                && verificarSenha(senha)){
            this.saldo -= valor;
            conta.saldo += conta.getSaldo() + valor;
            this.transferencias.add(new Transferencia(conta,this,valor));
            return true;
        }
        return false;
    }

    public void adicionarCartao(Cartao cartao, int senha){

        if(verificarSenha(senha) && cartao != null){
            for(int i = 0; i < this.cartoes.length; i++){
                if(this.cartoes[i] == null){
                    this.cartoes[i] = cartao;
                }
            }
        }
    }

    public void removerCartao(int indice, int senha){

        if((indice == 0 || indice == 1)
                && verificarSenha(senha)) {
            this.cartoes[indice] = new Cartao();
        };
    }

    private boolean verificarSenha(int senha){

        if(this.senha == senha){
            return true;
        }
        return false;
    }

    public double getSaldo() {
        return saldo;
    }

}
