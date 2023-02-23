package entities.model;

import java.time.LocalDate;

public class CartaoDeCredito extends Cartao {
    private double limite = 1000;

    public CartaoDeCredito() {
    }

    public CartaoDeCredito(Integer idCartao, Conta conta, Integer numeroCartao, LocalDate dataExpedicao, Integer codigoSeguranca, LocalDate vencimento, double limite) {
        super(idCartao, conta, numeroCartao, dataExpedicao, codigoSeguranca, TipoCartao.CREDITO.getValue(), vencimento);
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
