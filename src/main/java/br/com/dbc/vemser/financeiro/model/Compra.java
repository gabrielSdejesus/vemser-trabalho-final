package br.com.dbc.vemser.financeiro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Compra {
    private Integer idCompra;
    private Cartao cartao;
    private String docVendedor;
    private LocalDate data;
}
