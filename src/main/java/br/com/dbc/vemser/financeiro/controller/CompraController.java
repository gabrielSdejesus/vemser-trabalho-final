package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.dto.ClienteCreateDTO;
import br.com.dbc.vemser.financeiro.dto.ClienteDTO;
import br.com.dbc.vemser.financeiro.dto.CompraCreateDTO;
import br.com.dbc.vemser.financeiro.dto.CompraDTO;
import br.com.dbc.vemser.financeiro.service.CompraService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RequestMapping("/compra")
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;

    public ResponseEntity<CompraDTO> listarTodasCompras(){
        return null;
    }

    public ResponseEntity<CompraDTO> listarComprasDaConta(){
        return null;
    }

    public ResponseEntity<CompraDTO> criar(@RequestBody @Valid CompraCreateDTO compra){
        log.info("Criando compra!");
        log.info("compra Criado!");
        return null;
    }

    public ResponseEntity<CompraDTO> atualizar(@NotNull Long numeroDoCartao,
                                               @RequestBody @Valid CompraCreateDTO compra){
        log.info("Atualizando compra!");
        log.info("compra Atualizado!");
        return null;
    }

    public ResponseEntity<CompraDTO> deletar(@NotNull Integer numeroDaCompra){
        log.info("Deletando compra!");
        log.info("compra Deletado!");
        return null;
    }
}
