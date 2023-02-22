package entities.model;

import entities.interfaces.Exibicao;

public class Transferencia implements Exibicao {
    private Conta contaRecebeu;
    private Conta contaPagou;
    private double valor;

    public Transferencia(Conta contaRecebeu, Conta contaPagou, double valor) {
        this.contaRecebeu = contaRecebeu;
        this.contaPagou = contaPagou;
        this.valor = valor;
    }

    @Override
    public void exibir() {
        System.out.println("\tNúmero da conta que pagou: "+contaPagou.getNumero());
        System.out.println("\tNúmero da conta que recebeu: "+contaRecebeu.getNumero());
        System.out.printf("\tValor da transação R$ %.2f: \n", valor);
    }

    @Override
    public String toString() {
        return "Transferencia{" +
                "contaRecebeu=" + contaRecebeu +
                ", contaPagou=" + contaPagou +
                ", valor=" + valor +
                '}';
    }
}