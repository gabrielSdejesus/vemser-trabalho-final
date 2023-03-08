package br.com.dbc.vemser.financeiro.dto;

import br.com.dbc.vemser.financeiro.model.Cliente;
import br.com.dbc.vemser.financeiro.model.Status;
import lombok.Data;

@Data
public class ContaDTO{

    private Integer numeroConta;
    private Cliente cliente;
    private Integer agencia;
    private Double saldo;
    private Double chequeEspecial;
    private Status status;
}
