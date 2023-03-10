package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;

@Validated
public interface ControleAdicionar<ENTRADA, RETORNO> {
    @Operation(summary = "Adiciona no Banco de Dados", description = "Adiciona o dado no Banco de Dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Dado adicionado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<RETORNO> adicionar(@RequestBody @Valid ENTRADA dado,
                                      @RequestHeader("numeroConta") Integer numeroConta,
                                      @RequestHeader("senha") String senha) throws BancoDeDadosException, RegraDeNegocioException;
}
