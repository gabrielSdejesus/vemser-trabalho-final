package entities.model;

import java.time.LocalDate;

public class CartaoDeCredito extends Cartao {
    private double limite = 1000;
    private final int TIPO = 2;

    public CartaoDeCredito(Conta conta) {
        super(conta, 2);
    }

    public boolean pagarComCartao(double valor, String senha) {
        if(getConta().verificarSenha(senha)){
            return valor <= limiteRestante();
        }
        return false;
    }

    public double limiteRestante(){
        return limite - (super.getCompras().stream()
               .filter(compra -> compra.getData().getMonth() == LocalDate.now().getMonth())
               .mapToDouble(Compra::returnValorTotal)
               .sum());
    }

    public void exibirDadosCartao() {
        super.exibirDadosCartao();
        System.out.printf("\t\tLimite: R$%.2f\n", limiteRestante());
    }

    public int getTipo(){
        return TIPO;
    }
    public double getLimite(){
        return limite;
    }
}
