package br.com.dbc.vemser.financeiro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Cartao {

    private Long numeroCartao;
    private Integer numeroConta;
    private LocalDate dataExpedicao;
    private Integer codigoSeguranca;
    private TipoCartao tipo;
    private LocalDate vencimento;
    private Status status = Status.ATIVO;

}
