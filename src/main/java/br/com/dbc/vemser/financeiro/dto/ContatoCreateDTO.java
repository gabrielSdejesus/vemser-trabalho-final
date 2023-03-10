package br.com.dbc.vemser.financeiro.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContatoCreateDTO {

    @Hidden
    private Integer idCliente;
    @NotBlank(message = "Telefone não deve ser nulo!")
    @Schema(example = "089665478993")
    private String telefone;
    @Email(message = "Email inválido!")
    @Schema(example = "gabrieldejesus9905@gmail.com")
    private String email;
}