package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.dto.ItemCreateDTO;
import br.com.dbc.vemser.financeiro.dto.ItemDTO;
import br.com.dbc.vemser.financeiro.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RequestMapping("/item")
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/lista")
    public ResponseEntity<ItemDTO> listarTodosItens(){
        return null;
    }

    @GetMapping("/{idCompra}/compra")
    public ResponseEntity<ItemDTO> listarItensDaCompra(@NotNull Integer idCompra){
        return null;
    }

    @PostMapping
    public ResponseEntity<ItemDTO> criar(@RequestBody @Valid ItemCreateDTO item){
        log.info("Criando compra!");
        log.info("compra Criado!");
        return null;
    }

    @PutMapping("/{idItem}")
    public ResponseEntity<ItemDTO> atualizar(@NotNull Integer idItem,
                                               @RequestBody @Valid ItemCreateDTO item){
        log.info("Atualizando compra!");
        log.info("compra Atualizado!");
        return null;
    }

    @DeleteMapping("/{idItem}")
    public ResponseEntity<Void> deletar(@NotNull Integer idItem){
        log.info("Deletando compra!");
        log.info("compra Deletado!");
        return null;
    }
}
