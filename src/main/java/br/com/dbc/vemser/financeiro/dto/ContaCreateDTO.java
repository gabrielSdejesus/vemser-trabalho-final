package br.com.dbc.vemser.financeiro.dto;

import br.com.dbc.vemser.financeiro.model.Cliente;
import br.com.dbc.vemser.financeiro.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContaCreateDTO {
    @NotNull(message = "Cliente da conta nçao pode ser nulo!")
    private Cliente cliente;
    @NotBlank(message = "Senha da conta não pode ser nula!")
    @Size(min = 6, max = 6, message = "Senha da conta deve ter exatamente 6 caracteres!")
    private String senha;
    @NotBlank(message = "Agência da conta não pode ser nula!")
    private Integer agencia;
    @NotNull(message = "Saldo não deve ser nulo!")
    @PositiveOrZero(message = "Saldo não deve ser negativo!")
    private Double saldo;
    @NotNull(message = "Cheque especial não deve ser nulo!")
    @PositiveOrZero(message = "Cheque especial não deve ser negativo!")
    private Double chequeEspecial;
    @NotNull(message = "Status não deve ser nulo!")
    private Status status = Status.ATIVO;
}
