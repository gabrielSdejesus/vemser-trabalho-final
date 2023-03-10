package br.com.dbc.vemser.financeiro.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EnderecoCreateDTO {

    @Hidden
    private Integer idCliente;
    @NotBlank(message = "Logradouro não pode ser vazio ou nulo!")
    @Size(max = 250, message = "Logradouro longo demais!")
    @Schema(example = "Rua Tal")
    private String logradouro;
    @NotBlank(message = "Cidade não pode ser vazia ou nula!")
    @Size(max = 250)
    @Schema(example = "Salvador")
    private String cidade;
    @NotBlank(message = "Estado não pode ser vazio ou nulo!")
    @Size(min = 2, max = 2, message = "Estado inválido! (RJ) (SP) (BA) (MG)")
    @Schema(example = "BA")
    private String estado;
    @NotBlank(message = "País não pode ser vazio ou nulo!")
    @Schema(example = "Brasil")
    private String pais;
    @NotBlank(message = "CEP não pode ser vazio ou nulo!")
    @Size(min=8, max=8, message = "CEP inválido!")
    @Schema(example = "35845765")
    private String cep;
}
