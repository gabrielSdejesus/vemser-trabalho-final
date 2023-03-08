package br.com.dbc.vemser.financeiro.dto;

import br.com.dbc.vemser.financeiro.model.Status;
import lombok.Data;

@Data
public class ClienteDTO extends ClienteCreateDTO{
    private Integer idCliente;
    private Status status;
}