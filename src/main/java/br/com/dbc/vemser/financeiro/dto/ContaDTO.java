package br.com.dbc.vemser.financeiro.dto;

import br.com.dbc.vemser.financeiro.model.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ContaDTO{

    @Schema(example = "100000")
    private Integer numeroConta;
    private ClienteDTO cliente;
    @Schema(example = "1234")
    private Integer agencia;
    @Schema(example = "500")
    private Double saldo;
    @Schema(example = "200")
    private Double chequeEspecial;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Status status;
}
