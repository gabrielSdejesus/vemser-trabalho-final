package br.com.dbc.vemser.financeiro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Validated
public class ContaCreateDTO {

    @NotNull(message = "Cliente inválido!")
    @Valid()
    private ClienteCreateDTO clienteCreateDTO;
    @NotNull(message = "Contato inválido!")
    @Valid
    private ContatoCreateDTO contatoCreateDTO;
    @NotNull(message = "Endereço inválido!")
    @Valid
    private EnderecoCreateDTO enderecoCreateDTO;
    @NotBlank(message = "Senha inválida!")
    @Size(min = 6, max = 6, message = "Senha da conta deve ter exatamente 6 caracteres!")
    @Schema(example = "123456")
    private String senha;
    @NotNull(message = "Saldo inválido!")
    @PositiveOrZero(message = "Saldo inválido! Insira um saldo positivo!")
    @Schema(example = "500")
    private Double saldo;
}
