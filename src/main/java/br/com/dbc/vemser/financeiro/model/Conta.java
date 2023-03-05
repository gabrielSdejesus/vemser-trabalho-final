package br.com.dbc.vemser.financeiro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Conta {
    private Integer numeroConta;
    private Cliente cliente;
    private String senha;
    private Integer agencia;
    private Double saldo;
    private Double chequeEspecial = 200.0;
    private Status status;
}
