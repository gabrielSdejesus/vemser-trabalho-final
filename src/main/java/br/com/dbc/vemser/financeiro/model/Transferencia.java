package br.com.dbc.vemser.financeiro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transferencia{
    private Integer idTransferencia;//PK
    private Long contaEnviou;
    private Long contaRecebeu;
    private Double valor;
}