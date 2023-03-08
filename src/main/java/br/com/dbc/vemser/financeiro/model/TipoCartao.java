package br.com.dbc.vemser.financeiro.model;

import java.util.Arrays;

public enum TipoCartao {
    DEBITO(1),
    CREDITO(2);

    private final Integer tipo;

    TipoCartao(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getTipo() {
        return tipo;
    }

    public static TipoCartao getTipoCartao(Integer tipo) {
        return Arrays.stream(TipoCartao.values())
                .filter(tp -> tp.getTipo().equals(tipo))
                .findFirst()
                .get();
    }
}
