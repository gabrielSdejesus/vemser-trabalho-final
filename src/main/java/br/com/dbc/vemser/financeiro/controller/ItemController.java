package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.dto.ItemCreateDTO;
import br.com.dbc.vemser.financeiro.dto.ItemDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping("/item")
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/lista")
    public ResponseEntity<List<ItemDTO>> listarTodosItens(){
        return null;
    }

    @GetMapping("/{idCompra}/compra")
    public ResponseEntity<List<ItemDTO>> listarItensDaCompra(@NotNull @PathVariable("idCompra") Integer idCompra) throws BancoDeDadosException, RegraDeNegocioException {
        return new ResponseEntity<>(itemService.listarItensPorIdCompra(idCompra), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<List<ItemDTO>> criar(@RequestBody @Valid List<ItemCreateDTO> itensCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Criando compra!");
        log.info("compra Criado!");
        return new ResponseEntity<>(itemService.adicionar(itensCreateDTO), HttpStatus.OK);
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
