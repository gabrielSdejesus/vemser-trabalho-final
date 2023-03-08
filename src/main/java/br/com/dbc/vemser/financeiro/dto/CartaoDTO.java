package br.com.dbc.vemser.financeiro.dto;

import br.com.dbc.vemser.financeiro.model.TipoCartao;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartaoDTO extends CartaoCreateDTO {
    private String numeroCartao;
    private Double limite;

    public void setarLimite(Double valor){
        if(getTipo().equals(TipoCartao.CREDITO)){
            this.limite = valor;
        }
    }
}
