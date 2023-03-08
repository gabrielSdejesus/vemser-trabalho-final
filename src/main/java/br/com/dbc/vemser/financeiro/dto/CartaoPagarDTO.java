package br.com.dbc.vemser.financeiro.dto;

import br.com.dbc.vemser.financeiro.model.TipoCartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartaoPagarDTO {

    @NotNull
    @Valid
    private ContaAcessDTO contaAcessDTO;
    @NotNull(message = "Informe o número do cartão!")
    private Long numeroCartao;
    @NotNull(message = "Informe um valor!")
    @Positive(message = "Valor inválido! > 0")
    private Double valor;
    @NotNull(message = "Tipo inválido!")
    private TipoCartao tipoCartao;
    @NotNull(message = "Código inválido")
    @Size(min = 3, max = 3, message = "Código de segurança tem que possui 3 dígitos!")
    private Integer codigoSeguranca;
}
