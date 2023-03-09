package br.com.dbc.vemser.financeiro.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CompraDTO  {
    private Integer idCompra;
    private Long numeroCartao;
    private String docVendedor;
    private LocalDate data;
    private List<ItemDTO> itens;
}
