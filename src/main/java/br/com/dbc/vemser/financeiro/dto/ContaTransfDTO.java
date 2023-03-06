package br.com.dbc.vemser.financeiro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContaTransfDTO extends ContaAcessDTO{

    @NotNull(message = "Valor inválido!")
    @Positive(message = "Valor inválido!")
    private Double valor;
}
