package br.com.dbc.vemser.financeiro.model;

import java.time.LocalDate;

public class CartaoDeDebito extends Cartao {

    public CartaoDeDebito(){}

    public CartaoDeDebito(String numeroCartao, Conta conta, LocalDate dataExpedicao, Integer codigoSeguranca, TipoCartao tipo, LocalDate vencimento) {
        super(numeroCartao, conta, dataExpedicao, codigoSeguranca, tipo, vencimento);
    }
}
