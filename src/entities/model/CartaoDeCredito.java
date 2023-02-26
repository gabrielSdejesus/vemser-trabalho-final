package entities.model;

import java.time.LocalDate;

public class CartaoDeCredito extends Cartao {
    private double limite;

    public CartaoDeCredito() {
        this.limite = 1000;
    }

    public CartaoDeCredito(String numeroCartao, Conta conta, LocalDate dataExpedicao, Integer codigoSeguranca, TipoCartao tipo, LocalDate vencimento, double limite) {
        super(numeroCartao, conta, dataExpedicao, codigoSeguranca, tipo, vencimento);
        this.limite = limite;
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
