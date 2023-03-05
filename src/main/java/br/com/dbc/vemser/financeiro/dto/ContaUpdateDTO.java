package br.com.dbc.vemser.financeiro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContaUpdateDTO extends ContaAcessDTO{

    @NotBlank(message = "Senha inválida!")
    @Size(min = 6, max = 6, message = "Senha da conta deve ter exatamente 6 caracteres!")
    private String senhaNova;
}
