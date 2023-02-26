package entities.model;

public class Transferencia{

    private Integer idTransferencia;
    private Conta contaEnviou;
    private Conta contaRecebeu;
    private Double valor;

    public Transferencia(){};

    public Transferencia(Integer idTransferencia, Conta contaEnviou, Conta contaRecebeu, Double valor) {
        this.idTransferencia = idTransferencia;
        this.contaEnviou = contaEnviou;
        this.contaRecebeu = contaRecebeu;
        this.valor = valor;
    }

    public Integer getIdTransferencia() {
        return idTransferencia;
    }

    public void setIdTransferencia(Integer idTransferencia) {
        this.idTransferencia = idTransferencia;
    }

    public Conta getContaEnviou() {
        return contaEnviou;
    }

    public void setContaEnviou(Conta contaEnviou) {
        this.contaEnviou = contaEnviou;
    }

    public Conta getContaRecebeu() {
        return contaRecebeu;
    }

    public void setContaRecebeu(Conta contaRecebeu) {
        this.contaRecebeu = contaRecebeu;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        String sql = "\nTransferência" +
                "\n\tNome de quem enviou: " + contaEnviou.getCliente().getNome() +
                "\n\tNº da conta que enviou: " + contaEnviou.getNumeroConta() +
                "\n\tNome de quem recebeu: " + contaRecebeu.getCliente().getNome() +
                "\n\tNº da conta que recebeu: " + contaRecebeu.getNumeroConta() +
                "\n\tValor: R$" + valor + "\n";
        return sql;
    }
}