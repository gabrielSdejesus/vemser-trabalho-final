package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.dto.CompraCreateDTO;
import br.com.dbc.vemser.financeiro.dto.CompraDTO;
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

    @GetMapping("/lista")
    public ResponseEntity<List<CompraDTO>> listarTodasCompras(){
        return null;
    }

    @GetMapping("/{idCartao}/cartao")
    public ResponseEntity<List<CompraDTO>> listarComprasDoCartao(@NotNull Integer idCartao){
        return null;
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
