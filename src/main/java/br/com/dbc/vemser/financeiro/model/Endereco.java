package br.com.dbc.vemser.financeiro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Endereco {
    private Integer idEndereco;
    private Cliente cliente;
    private String logradouro;
    private String cidade;
    private String estado;
    private String pais;
    private String cep;
}
