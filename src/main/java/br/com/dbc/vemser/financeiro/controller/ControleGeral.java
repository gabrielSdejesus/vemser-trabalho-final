package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.dto.ContaAcessDTO;
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

import javax.validation.Valid;
import java.util.List;

@Validated
public interface ControleGeral<ENTRADA, RETORNO> {
    @Operation(summary = "Listar do Banco de Dados", description = "Lista todos os dados do Banco de Dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Dados retornados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<List<RETORNO>> listar(@Valid @RequestBody ContaAcessDTO acess) throws BancoDeDadosException;

    @Operation(summary = "Adicionar dado no Banco de Dados", description = "Adiciona um dado no Banco de Dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Dado adicionado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<RETORNO> adicionar(@Valid @RequestBody ENTRADA dado, @Valid @RequestBody ContaAcessDTO acess) throws BancoDeDadosException, RegraDeNegocioException;
}
