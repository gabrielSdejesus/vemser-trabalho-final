package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.dto.*;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.service.TransferenciaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/transferencia")
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
public class TransferenciaController implements ControleGeral<TransferenciaCreateDTO, TransferenciaDTO>{

    private final TransferenciaService transferenciaService;

    @GetMapping("/{idTransferencia}")
    public ResponseEntity<TransferenciaDTO> exibirTransferenciaPeloId(@PathVariable("idTransferencia") Integer idTransferencia) throws BancoDeDadosException {
        return ResponseEntity.ok(transferenciaService.retornarTransferencia(idTransferencia));
    }

    @GetMapping("/{idConta}/conta")
    public ResponseEntity<List<TransferenciaDTO>> exibirTransferenciasDaConta(@PathVariable("idConta") Integer idConta, @Valid @RequestBody ContaAcessDTO contaAcessDTO) throws BancoDeDadosException, RegraDeNegocioException {
        return ResponseEntity.ok(transferenciaService.listarTransferenciasDaConta(idConta, contaAcessDTO));
    }

    @Override
    public ResponseEntity<List<TransferenciaDTO>> listar(ContaAcessDTO acess) throws BancoDeDadosException {
        return ResponseEntity.ok(transferenciaService.listarTransferencias());
    }

    @Override
    public ResponseEntity<TransferenciaDTO> adicionar(TransferenciaCreateDTO dado, ContaAcessDTO acess) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Adicionando transferência!");
        TransferenciaDTO transferenciaDTO = transferenciaService.adicionarTransferencia(dado, acess);
        log.info("Transferência adicionada!");
        return ResponseEntity.ok(transferenciaDTO);
    }
}
