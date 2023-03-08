package br.com.dbc.vemser.financeiro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContaAcessDTO {

    @NotNull(message = "Número inválido!")
    @Digits(integer = 6, fraction = 0, message = "Número da conta deve ter 6 dígitos!")
    private Integer numeroConta;
    @NotBlank(message = "Senha inválida!")
    @Size(min = 6, max = 6, message = "Senha da conta deve ter exatamente 6 caracteres!")
    private String senha;
}
