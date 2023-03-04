package br.com.dbc.vemser.financeiro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cliente {
    private Integer idCliente;
    private String cpf;
    private String nome;
    private Status status = Status.ATIVO;
}
