package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.dto.*;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.service.ContaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/cliente")
    public ResponseEntity<ContaDTO> retornarContaCliente(@RequestBody @Valid ContaAcessDTO contaAcessDTO) throws BancoDeDadosException, RegraDeNegocioException {
        return ResponseEntity.ok(contaService.retornarContaCliente(contaAcessDTO));
    }

    @PostMapping
    public ResponseEntity<ContaDTO> criar(@RequestBody @Valid ContaCreateDTO contaCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Criando Conta!");
        ContaDTO contaDTO = contaService.criar(contaCreateDTO);
        log.info("Conta Criada!");
        return ResponseEntity.ok(contaDTO);
    }

    @PutMapping("/alterarsenha")
    public ResponseEntity<ContaDTO> alterarSenha(@RequestBody @Valid ContaUpdateDTO contaUpdateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Alterando Senha!");
        ContaDTO contaDTO = contaService.alterarSenha(contaUpdateDTO);
        log.info("Senha Alterada!");
        return ResponseEntity.ok(contaDTO);
    }

    @PutMapping("/sacar")
    public ResponseEntity<ContaDTO> sacar(@RequestBody @Valid ContaTransfDTO contaTransfDTO) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Sacando valor: R$" + contaTransfDTO.getValor());
        ContaDTO contaDTO = contaService.sacar(contaTransfDTO);
        log.info("Valor Sacado: R$" + contaTransfDTO.getValor());
        return ResponseEntity.ok(contaDTO);
    }

    @PutMapping("/depositar")
    public ResponseEntity<ContaDTO> depositar(@RequestBody @Valid ContaTransfDTO contaTransfDTO) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Depositando valor: R$" + contaTransfDTO.getValor());
        ContaDTO contaDTO = contaService.depositar(contaTransfDTO);
        log.info("Valor Depositado: R$" + contaTransfDTO.getValor());
        return ResponseEntity.ok(contaDTO);
    }

    @PutMapping("/{cpf}/reativar")
    public ResponseEntity<Void> reativarConta(@PathVariable("cpf") String cpf) throws BancoDeDadosException {
        log.info("Reativando Conta!");
        contaService.reativarConta(cpf);
        log.info("Conta Reativada!");
        return ResponseEntity.ok().build();
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
