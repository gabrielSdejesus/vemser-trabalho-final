package br.com.dbc.vemser.financeiro.dto;

import br.com.dbc.vemser.financeiro.model.Cartao;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompraCreateDTO {
    @NotNull(message = "Cartão não pode ser nulo!")
    private Long numeroCartao;
    @NotBlank(message = "Documento do vendedor não pode ser nulo!")
    private String docVendedor;
    @NotNull(message = "Data não pode ser nula!")
    @PastOrPresent(message = "Data da compra não pode ser uma data no futuro!")
    private LocalDate data;
    @NotNull
    private List<ItemCreateDTO> itens;
}
