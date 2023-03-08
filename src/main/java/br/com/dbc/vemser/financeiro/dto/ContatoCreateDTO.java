package br.com.dbc.vemser.financeiro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContatoCreateDTO {
    private Integer idCliente;
    @NotBlank(message = "Telefone não deve ser nulo!")
    private String telefone;
    @Email(message = "Email inválido!")
    private String email;
}