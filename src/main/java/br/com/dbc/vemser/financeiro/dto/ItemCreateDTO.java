package br.com.dbc.vemser.financeiro.dto;

import br.com.dbc.vemser.financeiro.model.Compra;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemCreateDTO {
    @NotNull(message = "Compra não deve ser nula!")
    private Integer idCompra;
    @NotBlank(message = "Nome do item não deve ser nulo!")
    @Size(max = 100, message = "Nome do item longo demais!")
    private String nome;
    @NotNull(message = "Valor do item não pode ser nulo!")
    @Positive(message = "Valor do item não pode ser negativo ou zero!")
    private Double valor;
    @NotNull(message = "Quantidade do item não pode ser nulo!")
    @Positive(message = "Quantidade do item não pode ser negativo ou zero!")
    private Integer quantidade;
}
