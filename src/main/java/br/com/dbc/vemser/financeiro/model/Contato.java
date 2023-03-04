package br.com.dbc.vemser.financeiro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Contato {
    private Integer idContato;//PK
    private Cliente cliente;
    private String telefone;
    private String email;
}
