package br.com.dbc.vemser.financeiro.dto;

import br.com.dbc.vemser.financeiro.dto.CartaoCreateDTO;
import lombok.Data;

@Data
public class CartaoDTO extends CartaoCreateDTO {
    private String numeroCartao;
}
