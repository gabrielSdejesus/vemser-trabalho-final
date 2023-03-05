package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.dto.ContaAcessDTO;
import br.com.dbc.vemser.financeiro.dto.ContaCreateDTO;
import br.com.dbc.vemser.financeiro.dto.ContaDTO;
import br.com.dbc.vemser.financeiro.dto.ContaUpdateDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.service.ContaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/conta")
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
public class ContaController {

    private final ContaService contaService;

    @GetMapping("/lista")
    public ResponseEntity<List<ContaDTO>> listarContas() throws BancoDeDadosException, RegraDeNegocioException {
        return ResponseEntity.ok(contaService.listar());
    }

    @GetMapping("/{idCliente}/cliente")
    public ResponseEntity<ContaDTO> retornarContaCliente(@PathVariable("idCliente") Integer idCliente,
            @RequestBody ContaAcessDTO contaAcessDTO) throws BancoDeDadosException, RegraDeNegocioException {
        return ResponseEntity.ok(contaService.retornarContaCliente(idCliente,contaAcessDTO));
    }

    @PostMapping
    public ResponseEntity<ContaDTO> criar(@RequestBody ContaCreateDTO contaCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Criando Conta!");
        ContaDTO contaDTO = contaService.criar(contaCreateDTO);
        log.info("Conta Criada!");
        return ResponseEntity.ok(contaDTO);
    }

    @PutMapping("/{idCliente}/alterarsenha")
    public ResponseEntity<ContaDTO> alterarSenha(@PathVariable("idCliente") Integer idCliente,
                                              @RequestBody ContaUpdateDTO contaUpdateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Alterando Senha!");
        ContaDTO contaDTO = contaService.alterarSenha(idCliente,contaUpdateDTO);
        log.info("Senha Alterada!");
        return ResponseEntity.ok(contaDTO);
    }

    @DeleteMapping("/{idCliente}/{numeroConta}/delete")
    public ResponseEntity<Void> removerConta(@PathVariable("idCliente") Integer idCliente,
                                              @PathVariable("numeroConta") Integer numeroConta) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Deletando Conta!");
        contaService.removerConta(idCliente,numeroConta);
        log.info("Conta Deletada!");
        return ResponseEntity.ok().build();
    }
}
