package br.com.dbc.vemser.financeiro.dto;

import br.com.dbc.vemser.financeiro.model.Conta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransferenciaCreateDTO {
    @NotNull(message = "Conta que enviou não pode ser nula!")
    private Conta contaEnviou;
    @NotNull(message = "Conta que recebeu não pode ser nula!")
    private Conta contaRecebeu;
    @NotNull(message = "Valor da transferencia não pode ser nulo!")
    @Positive(message = "Valor da transferencia não pode ser negativo ou zero!")
    private Double valor;
}
