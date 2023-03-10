package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.dto.*;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.service.TransferenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/transferencia")
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
public class TransferenciaController implements ControleListar<List<TransferenciaDTO>>,
        ControleAdicionar<TransferenciaCreateDTO, TransferenciaDTO>,
        ControleListarPorID<TransferenciaDTO>{

    private final TransferenciaService transferenciaService;

    @Operation(summary = "Listar do Banco de Dados as transferências de uma conta", description = "Lista do Banco de Dados todas as transferência que uma conta enviou")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Transferencias retornadas"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/conta")
    public ResponseEntity<List<TransferenciaDTO>> exibirTransferenciasDaConta(@RequestHeader("numeroConta") Integer numeroConta,
                                                                              @RequestHeader("senha") String senha) throws BancoDeDadosException, RegraDeNegocioException {
        return ResponseEntity.ok(transferenciaService.listarTransferenciasDaConta(numeroConta, senha));
    }

    @Override
    public ResponseEntity<List<TransferenciaDTO>> listar() throws BancoDeDadosException {//função do ADM
        return ResponseEntity.ok(transferenciaService.listarTransferencias());
    }

    @Override
    public ResponseEntity<TransferenciaDTO> adicionar(TransferenciaCreateDTO dado,
                                                      Integer numeroConta,
                                                      String senha) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Adicionando transferência!");
        TransferenciaDTO transferenciaDTO = transferenciaService.adicionarTransferencia(dado, numeroConta, senha);
        log.info("Transferência adicionada!");
        return ResponseEntity.ok(transferenciaDTO);
    }

    @Override
    public ResponseEntity<TransferenciaDTO> listarPorId(Integer id, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        return ResponseEntity.ok(transferenciaService.retornarTransferencia(id, numeroConta, senha));
    }
}
