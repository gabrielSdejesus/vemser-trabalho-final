package br.com.dbc.vemser.financeiro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transferencia{
    private Integer idTransferencia;//PK
    private Conta contaEnviou;
    private Conta contaRecebeu;
    private Double valor;
}