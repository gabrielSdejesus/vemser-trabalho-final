package entities.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class CartaoDeCredito extends Cartao {
    private double limite = 1000;
    private final int TIPO = 2;

    public CartaoDeCredito(Conta conta) {
        super(conta);
    }

    public boolean pagar(double valor, String senha) {
        return valor <= limiteRestante();
    }

    public double limiteRestante(){
        return limite - (super.getCompras().stream()
               .filter(compra -> compra.getData().getMonth() == LocalDate.now().getMonth())
               .mapToDouble(Compra::returnValorTotal)
               .sum());
    }

    public void setLimite(double novoLimite) {
        limite = novoLimite;
    }

    public double getLimite(){return limite;}

    public int getTipo(){
        return TIPO;
    }
}
