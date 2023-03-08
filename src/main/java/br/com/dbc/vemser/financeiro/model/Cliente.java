package br.com.dbc.vemser.financeiro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cliente {
    private Integer idCliente;
    private String cpf;
    private String nome;
    private Status status;

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", status=" + status +
                '}';
    }
}
