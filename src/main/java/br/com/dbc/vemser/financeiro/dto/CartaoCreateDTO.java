package br.com.dbc.vemser.financeiro.dto;

import br.com.dbc.vemser.financeiro.model.Status;
import br.com.dbc.vemser.financeiro.model.TipoCartao;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CartaoCreateDTO {

    private LocalDate dataExpedicao;
    private Integer codigoSeguranca;
    private TipoCartao tipo;
    private LocalDate vencimento;
    private Status status = Status.ATIVO;

}
