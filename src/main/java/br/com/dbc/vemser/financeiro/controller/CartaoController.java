package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.dto.CartaoCreateDTO;
import br.com.dbc.vemser.financeiro.model.Cartao;
import br.com.dbc.vemser.financeiro.service.CartaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping("/cartao")
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
public class CartaoController {

    private final CartaoService cartaoService;

    @GetMapping("/{numeroConta}")
    public ResponseEntity<List<CartaoDTO>> listarPorIdConta(@PathVariable("numeroConta") Integer numeroConta) {
        return new ResponseEntity<>(cartaoService.listarPorIdConta(numeroConta), HttpStatus.OK);
    }

    @GetMapping("/{numeroConta}")
    public ResponseEntity<CartaoDTO> criar(@PathVariable("numeroConta") Integer numeroConta, @RequestBody CartaoCreateDTO cartaoCreateDTO) {
        return new ResponseEntity<>(cartaoService.criar(numeroConta, cartaoCreateDTO), HttpStatus.OK);
    }

    @PutMapping("/{numeroCartao}")
    public ResponseEntity<CartaoDTO> atualizar(@PathVariable("numeroCartao") Integer numeroCartao, @RequestBody CartaoCreateDTO cartaoCreateDTO) {
        return new ResponseEntity<>(cartaoService.atualizar(numeroCartao, cartaoCreateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{numeroCartao}")
    public ResponseEntity<Void> deletar(@NotNull @PathVariable("idCartao") Integer numeroCartao) {
        cartaoService.deletar(numeroCartao);
        return ResponseEntity.ok().build();
    }

}
