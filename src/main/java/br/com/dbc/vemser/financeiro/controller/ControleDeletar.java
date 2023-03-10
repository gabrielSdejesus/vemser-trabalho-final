package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface ControleDeletar {
    @Operation(summary = "Deleta um dado do Banco de Dados", description = "Deleta um dado do Banco de Dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Dado deletado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Boolean> deletar(@NotNull @PathVariable("id") Integer id,
                                      @RequestHeader("numeroConta") Integer numeroConta,
                                      @RequestHeader("senha") String senha) throws BancoDeDadosException, RegraDeNegocioException;
}
