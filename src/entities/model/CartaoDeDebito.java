package entities.model;

public class CartaoDeDebito extends Cartao {
    private final int TIPO = 1;

    public CartaoDeDebito(Conta conta) {
        super(conta);
    }

    public int getTipo(){
        return TIPO;
    }

}
