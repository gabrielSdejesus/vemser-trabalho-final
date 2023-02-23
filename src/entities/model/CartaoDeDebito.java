package entities.model;

import java.time.LocalDate;

public class CartaoDeDebito extends Cartao {

    public CartaoDeDebito(){}

    public CartaoDeDebito(Integer idCartao, Conta conta, String numeroCartao, LocalDate dataExpedicao, Integer codigoSeguranca, LocalDate vencimento) {
        super(idCartao, conta, numeroCartao, dataExpedicao, codigoSeguranca, TipoCartao.DEBITO.getTipo(), vencimento);
    }


}
