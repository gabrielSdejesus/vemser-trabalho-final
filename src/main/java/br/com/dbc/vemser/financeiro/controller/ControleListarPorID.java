package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@Validated
public interface ControleListarPorID<RETORNO> {
    @Operation(summary = "Retorna do Banco de Dados", description = "Retorna do Banco de Dados um dado pelo seu id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Dado retornado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<RETORNO> listarPorId(@PathVariable("id") Integer id,
                                        @RequestHeader("numeroConta") Integer numeroConta,
                                        @RequestHeader("senha") String senha) throws BancoDeDadosException, RegraDeNegocioException;
}
