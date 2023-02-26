package entities.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class Compra {

    private Integer idCompra;
    private Cartao cartao;
    private String docVendedor;
    private LocalDate data;

    public Compra() {
    }

    public Compra(Integer idCompra, Cartao cartao, String docVendedor, LocalDate data) {
        this.cartao = cartao;
        this.docVendedor = docVendedor;
        this.data = data;
    }

    public Integer getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public String getDocVendedor() {
        return docVendedor;
    }

    public void setDocVendedor(String docVendedor) {
        this.docVendedor = docVendedor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Compra{" +
                "idCompra=" + idCompra +
                ", nDoCartao=" + cartao.getNumeroCartao() +
                ", nomeDoCliente= " + cartao.getConta().getCliente().getNome() +
                ", docVendedor='" + docVendedor + '\'' +
                ", data=" + data +
                '}';
    }
}
