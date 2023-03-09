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
import java.util.Map;

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
    public ResponseEntity<ContaDTO> retornarContaCliente(@RequestHeader("numeroConta") Integer numeroConta,
                                                         @RequestHeader("senha") String senha) throws BancoDeDadosException, RegraDeNegocioException {
        return ResponseEntity.ok(contaService.retornarContaCliente(numeroConta, senha));
    }

    @PostMapping
    public ResponseEntity<ContaDTO> criar(@RequestBody @Valid ContaCreateDTO contaCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Criando Conta!");
        ContaDTO contaDTO = contaService.criar(contaCreateDTO);
        log.info("Conta Criada!");
        return ResponseEntity.ok(contaDTO);
    }

    @PutMapping("/alterarsenha")
    public ResponseEntity<ContaDTO> alterarSenha(@RequestBody @Valid ContaUpdateDTO contaUpdateDTO,
                                                 @RequestHeader("numeroConta") Integer numeroConta,
                                                 @RequestHeader("senha") String senha) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Alterando Senha!");
        ContaDTO contaDTO = contaService.alterarSenha(contaUpdateDTO.getSenhaNova(), numeroConta, senha);
        log.info("Senha Alterada!");
        return ResponseEntity.ok(contaDTO);
    }

    @PutMapping("/sacar/{valor}")
    public ResponseEntity<ContaDTO> sacar(@PathVariable("valor") Double valor,
                                          @RequestHeader("numeroConta") Integer numeroConta,
                                          @RequestHeader("senha") String senha) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Sacando valor: R$" + valor);
        ContaDTO contaDTO = contaService.sacar(valor, numeroConta, senha);
        log.info("Valor Sacado: R$" + valor);
        return ResponseEntity.ok(contaDTO);
    }

    @PutMapping("/depositar/{valor}")
    public ResponseEntity<ContaDTO> depositar(@PathVariable("valor") Double valor,
                                              @RequestHeader("numeroConta") Integer numeroConta,
                                              @RequestHeader("senha") String senha) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Depositando valor: R$" + valor);
        ContaDTO contaDTO = contaService.depositar(valor, numeroConta, senha);
        log.info("Valor Depositado: R$" + valor);
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
