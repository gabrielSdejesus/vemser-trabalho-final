package entities.model;

import java.time.LocalDate;

public abstract class Cartao {
    private Integer idCartao;
    private Conta conta;
    private Integer numeroCartao;
    private LocalDate dataExpedicao;
    private Integer codigoSeguranca;
    private Integer tipo;
    private LocalDate vencimento;

    public Cartao() {
    }

    public Cartao(Integer idCartao, Conta conta, Integer numeroCartao, LocalDate dataExpedicao, Integer codigoSeguranca, Integer tipo, LocalDate vencimento) {
        this.idCartao = idCartao;
        this.conta = conta;
        this.numeroCartao = numeroCartao;
        this.dataExpedicao = dataExpedicao;
        this.codigoSeguranca = codigoSeguranca;
        this.tipo = tipo;
        this.vencimento = vencimento;
    }

    public Integer getIdCartao() {
        return idCartao;
    }

    public void setIdCartao(Integer idCartao) {
        this.idCartao = idCartao;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Integer getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(Integer numeroCartao) {
        this.numeroCartao = numeroCartao;
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

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    @Override
    public String toString() {
        return "Cartao{" +
                "idCartao=" + idCartao +
                ", conta=" + conta +
                ", numeroCartao=" + numeroCartao +
                ", dataExpedicao=" + dataExpedicao +
                ", codigoSeguranca=" + codigoSeguranca +
                ", tipo=" + tipo +
                ", vencimento=" + vencimento +
                '}';
    }
}
