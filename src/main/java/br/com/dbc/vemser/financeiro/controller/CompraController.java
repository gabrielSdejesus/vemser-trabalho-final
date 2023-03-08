package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.dto.CompraCreateDTO;
import br.com.dbc.vemser.financeiro.dto.CompraDTO;
import br.com.dbc.vemser.financeiro.dto.ContaAcessDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.service.CompraService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping("/compra")
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;

    // admin
    @GetMapping("/lista")
    public ResponseEntity<List<CompraDTO>> listarTodasCompras(){
        return null;
    }

    @GetMapping("/{numeroCartao}/cartao")
    public ResponseEntity<List<CompraDTO>> listarComprasDoCartao(@NotNull @PathVariable("numeroCartao") Long numeroCartao, @Valid @RequestBody ContaAcessDTO contaAcessDTO) throws BancoDeDadosException, RegraDeNegocioException {
        return ResponseEntity.ok(compraService.retornarComprasCartao(numeroCartao, contaAcessDTO));
    }

    @PostMapping
    public ResponseEntity<CompraDTO> criar(@RequestBody @Valid CompraCreateDTO compra){
        log.info("Criando compra!");
        log.info("compra Criado!");
        return null;
    }

    @PutMapping("/{idCompra}")
    public ResponseEntity<CompraDTO> atualizar(@NotNull Integer idCompra,
                                               @RequestBody @Valid CompraCreateDTO compra){
        log.info("Atualizando compra!");
        log.info("compra Atualizado!");
        return null;
    }

    @DeleteMapping("/{idCompra}")
    public ResponseEntity<Void> deletar(@NotNull Integer idCompra){
        log.info("Deletando compra!");
        log.info("compra Deletado!");
        return null;
    }
}
