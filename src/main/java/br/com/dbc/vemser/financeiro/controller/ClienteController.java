package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.dto.ClienteCreateDTO;
import br.com.dbc.vemser.financeiro.dto.ClienteDTO;
import br.com.dbc.vemser.financeiro.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping("/cliente")
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping("/lista")
    public ResponseEntity<List<ClienteDTO>> listarTodosClientes(){
        return null;
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> exibirCliente(@NotNull Integer idCliente){
        return null;
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> criar(@RequestBody @Valid ClienteCreateDTO cliente){
        log.info("Criando Cliente!");
        log.info("Cliente Criado!");
        return null;
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> atualizar(@NotNull Integer idCliente,
                                                @RequestBody @Valid ClienteCreateDTO cliente){
        log.info("Atualizando Cliente!");
        log.info("Cliente Atualizado!");
        return null;
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> deletar(@NotNull Integer idCliente){
        log.info("Deletando Cliente!");
        log.info("Cliente Deletado!");
        return null;
    }
}
