package br.com.dbc.vemser.financeiro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {
    private Integer idItem;//PK
    private Integer idCompra;
    private String nome;
    private Double valor;
    private Integer quantidade;
}
