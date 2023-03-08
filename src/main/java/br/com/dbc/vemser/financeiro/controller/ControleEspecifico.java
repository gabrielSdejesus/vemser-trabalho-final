package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.dto.ContaAcessDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface ControleEspecifico<ENTRADA, RETORNO> {
    @Operation(summary = "Remover um dado do Banco de Dados", description = "Remove um dado do Banco de Dados pelo id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Dados removido"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{id}")
    ResponseEntity<RETORNO> deletar(@NotNull @PathVariable("id") Integer id, @Valid @RequestBody ContaAcessDTO acess);

    @Operation(summary = "Atualizar dado no Banco de Dados", description = "Atualiza um dado no Banco de Dados pelo id, com todos os campos preenchidos (mesmo que só alguns mudem)")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Dado atualizado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{id}")
    ResponseEntity<RETORNO> atualizar(@NotNull @PathVariable("id") Integer id, @Valid @RequestBody ENTRADA dado, @Valid @RequestBody ContaAcessDTO acess);
}
