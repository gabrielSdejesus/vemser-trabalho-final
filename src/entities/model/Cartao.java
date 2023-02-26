package entities.model;

import java.time.LocalDate;

public abstract class Cartao {

    private String numeroCartao;
    private Conta conta;
    private LocalDate dataExpedicao;
    private Integer codigoSeguranca;
    private TipoCartao tipo;
    private LocalDate vencimento;
    private Status status = Status.ATIVO;

    public Cartao() {
    }

    public Cartao(String numeroCartao, Conta conta, LocalDate dataExpedicao, Integer codigoSeguranca, TipoCartao tipo, LocalDate vencimento) {
        this.numeroCartao = numeroCartao;
        this.conta = conta;
        this.dataExpedicao = dataExpedicao;
        this.codigoSeguranca = codigoSeguranca;
        this.tipo = tipo;
        this.vencimento = vencimento;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public LocalDate getDataExpedicao() {
        return dataExpedicao;
    }

    public void setDataExpedicao(LocalDate dataExpedicao) {
        this.dataExpedicao = dataExpedicao;
    }

    public Integer getCodigoSeguranca() {
        return codigoSeguranca;
    }

    public void setCodigoSeguranca(Integer codigoSeguranca) {
        this.codigoSeguranca = codigoSeguranca;
    }

    public TipoCartao getTipo() {
        return tipo;
    }

    public void setTipo(TipoCartao tipo) {
        this.tipo = tipo;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Cartao{" +
                "numeroCartao='" + numeroCartao + '\'' +
                ", conta=" + conta +
                ", dataExpedicao=" + dataExpedicao +
                ", codigoSeguranca=" + codigoSeguranca +
                ", tipo=" + tipo +
                ", vencimento=" + vencimento +
                ", status=" + status +
                '}';
    }
}
