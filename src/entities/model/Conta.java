package entities.model;

public class Conta {

    private Integer numeroConta;
    private Cliente cliente;
    private String senha;
    private Integer agencia;
    private Double saldo;
    private Double chequeEspecial;
    private Integer status = 1;

    public Conta(){}

    public Conta(Integer numeroConta, Integer agencia, Cliente cliente, String senha, Double saldo, Double chequeEspecial) {
        this.numeroConta = numeroConta;
        this.cliente = cliente;
        this.senha = senha;
        this.agencia = agencia;
        this.saldo = saldo;
        this.chequeEspecial = chequeEspecial;
    }

    public Integer getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(Integer numeroConta) {
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

    public Integer getAgencia() {
        return agencia;
    }

    public void setAgencia(Integer agencia) {
        this.agencia = agencia;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Double getChequeEspecial() {
        return chequeEspecial;
    }

    public void setChequeEspecial(Double chequeEspecial) {
        this.chequeEspecial = chequeEspecial;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "numeroConta=" + numeroConta +
                ", cliente=" + cliente +
                ", senha='" + senha + '\'' +
                ", agencia=" + agencia +
                ", saldo=" + saldo +
                ", chequeEspecial=" + chequeEspecial +
                ", ativo=" + status +
                '}';
    }
}
