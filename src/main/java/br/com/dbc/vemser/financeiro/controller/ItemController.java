package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.dto.ItemCreateDTO;
import br.com.dbc.vemser.financeiro.dto.ItemDTO;
import br.com.dbc.vemser.financeiro.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RequestMapping("/item")
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    public ResponseEntity<ItemDTO> listarTodosItens(){
        return null;
    }

    public ResponseEntity<ItemDTO> listarItensDaCompra(){
        return null;
    }

    public ResponseEntity<ItemDTO> criar(@RequestBody @Valid ItemCreateDTO item){
        log.info("Criando compra!");
        log.info("compra Criado!");
        return null;
    }

    public ResponseEntity<ItemDTO> atualizar(@NotNull Long numeroDaCompra,
                                               @RequestBody @Valid ItemCreateDTO item){
        log.info("Atualizando compra!");
        log.info("compra Atualizado!");
        return null;
    }

    public ResponseEntity<Void> deletar(@NotNull Integer idItem){
        log.info("Deletando compra!");
        log.info("compra Deletado!");
        return null;
    }
}
