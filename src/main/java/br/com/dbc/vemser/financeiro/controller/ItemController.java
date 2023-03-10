package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.dto.ItemCreateDTO;
import br.com.dbc.vemser.financeiro.dto.ItemDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public class ItemController implements ControleListar<List<ItemDTO>>,
        ControleAdicionar<List<ItemCreateDTO>, List<ItemDTO>>,
        ControleDeletar{

    private final ItemService itemService;

    @Operation(summary = "Listar os itens de uma compra do Banco de Dados", description = "Lista todos os itens de uma compra do Banco de Dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Itens retornados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idCompra}/compra")
    public ResponseEntity<List<ItemDTO>> listarItensDaCompra(@NotNull @PathVariable("idCompra") Integer idCompra) throws BancoDeDadosException, RegraDeNegocioException {
        return new ResponseEntity<>(itemService.listarItensPorIdCompra(idCompra), HttpStatus.OK);
    }

    @PutMapping("/{idItem}")
    public ResponseEntity<ItemDTO> atualizar(@NotNull @PathVariable("idItem") Integer idItem,
                                               @RequestBody @Valid ItemCreateDTO itemCreateDTO) throws BancoDeDadosException {
        log.info("Atualizando compra!");
        log.info("compra Atualizado!");
        return new ResponseEntity<>(itemService.atualizar(idItem, itemCreateDTO), HttpStatus.OK);
    }

    @Override
    @GetMapping("/lista")
    public ResponseEntity<List<ItemDTO>> listar() throws BancoDeDadosException, RegraDeNegocioException {//Função do ADM
        return ResponseEntity.ok(this.itemService.listar());
    }

    @Override
    public ResponseEntity<List<ItemDTO>> adicionar(List<ItemCreateDTO> dado, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Criando compra!");
        log.info("compra Criado!");
        return new ResponseEntity<>(itemService.adicionar(dado, numeroConta, senha), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Boolean> deletar(Integer id, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Deletando compra...");
        Boolean deletado = itemService.deletar(id, numeroConta, senha);
        log.info("compra Deletada!");
        return ResponseEntity.ok(deletado);
    }
}
