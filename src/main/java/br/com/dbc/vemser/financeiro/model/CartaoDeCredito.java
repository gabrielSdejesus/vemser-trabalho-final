package br.com.dbc.vemser.financeiro.model;

import java.time.LocalDate;

public class CartaoDeCredito extends Cartao {
    private double limite;

    public CartaoDeCredito() {
        this.limite = 1000;
    }


    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    @Override
    public String toString() {
        return  super.toString() + "\n CartaoDeCredito{" +
                "limite=" + limite +
                '}';
    }
}
