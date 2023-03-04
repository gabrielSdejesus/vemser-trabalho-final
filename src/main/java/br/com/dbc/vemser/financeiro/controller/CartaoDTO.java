package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.dto.CartaoCreateDTO;
import lombok.Data;

@Data
public class CartaoDTO extends CartaoCreateDTO {
    private String numeroCartao;
}
