package br.com.dbc.vemser.financeiro.dto;

import br.com.dbc.vemser.financeiro.model.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;

@Data
public class ClienteDTO extends ClienteCreateDTO{

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer idCliente;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Status status;
}