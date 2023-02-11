package entities.model;

public class Transferencia {
    private Conta contaRecebeu;
    private Conta contaPagou;
    private double valor;

    public Transferencia(Conta contaRecebeu, Conta contaPagou, double valor) {
        this.contaRecebeu = contaRecebeu;
        this.contaPagou = contaPagou;
        this.valor = valor;
    }
}