package entities.model;

public class Conta {

    private int numeroConta;
    private Cliente cliente;
    private String senha;
    private int agencia;
    private double saldo;
    private double chequeEspecial;

    public Conta(){}

    public Conta(int numeroConta, int agencia, Cliente cliente, String senha) {
        this.numeroConta = numeroConta;
        this.cliente = cliente;
        this.senha = senha;
        this.agencia = agencia;
        this.saldo = saldo;
        this.chequeEspecial = chequeEspecial;
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(int numeroConta) {
        this.numeroConta = numeroConta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getAgencia() {
        return agencia;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getChequeEspecial() {
        return chequeEspecial;
    }

    public void setChequeEspecial(double chequeEspecial) {
        this.chequeEspecial = chequeEspecial;
    }

    @Override
    public String toString() {
        return "Cliente: " + getCliente().getNome() + " | " + "Numero da conta: " + getNumeroConta() + " | " + "Agencia: " + getAgencia();
    }
}
