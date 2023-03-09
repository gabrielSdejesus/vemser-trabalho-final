package br.com.dbc.vemser.financeiro.dto;

import br.com.dbc.vemser.financeiro.model.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ContaDTO{

    private Integer numeroConta;
    private ClienteDTO cliente;
    private Integer agencia;
    private Double saldo;
    private Double chequeEspecial;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Status status;
}
