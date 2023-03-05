package br.com.dbc.vemser.financeiro.dto;

import br.com.dbc.vemser.financeiro.model.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EnderecoCreateDTO {

    @NotNull(message = "Necessário informar um cliente válido!")
    private Integer idCliente;
    @NotBlank(message = "Logradouro não pode ser vazio ou nulo!")
    @Size(max = 250, message = "Logradouro longo demais!")
    private String logradouro;
    @NotBlank(message = "Cidade não pode ser vazia ou nula!")
    @Size(max = 250)
    private String cidade;
    @NotBlank(message = "Estado não pode ser vazio ou nulo!")
    private String estado;
    @NotBlank(message = "País não pode ser vazio ou nulo!")
    private String pais;
    @NotBlank(message = "CEP não pode ser vazio ou nulo!")
    @Size(min=8, max=8, message = "CEP inválido!")
    private String cep;
}