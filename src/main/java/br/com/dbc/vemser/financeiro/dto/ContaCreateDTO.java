package br.com.dbc.vemser.financeiro.dto;

import br.com.dbc.vemser.financeiro.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContaCreateDTO {

    @NotNull(message = "Cliente inválido!")
    private Integer idCliente;
    @NotBlank(message = "CPF inválido!")
    private String cpf;
    @NotBlank(message = "Senha inválida!")
    @Size(min = 6, max = 6, message = "Senha da conta deve ter exatamente 6 caracteres!")
    private String senha;
    @NotNull(message = "Saldo inválido!")
    @PositiveOrZero(message = "Saldo inválido! Insira um saldo positivo!")
    private Double saldo;
}
