package br.com.dbc.vemser.financeiro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClienteCreateDTO {
    @NotBlank(message = "Nome não pode ser nulo!")
    private String nome;
    @NotBlank(message = "CPF não pode ser nulo!")
    @Size(min = 11, max = 11, message = "CPF inválido!")
    private String cpf;
}
