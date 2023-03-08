package br.com.dbc.vemser.financeiro.dto;

import lombok.Data;

@Data
public class CartaoDTO extends CartaoCreateDTO {
    private String numeroCartao;
}
