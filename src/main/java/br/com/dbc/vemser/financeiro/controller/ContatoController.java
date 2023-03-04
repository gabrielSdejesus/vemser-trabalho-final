package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.dto.ContatoCreateDTO;
import br.com.dbc.vemser.financeiro.dto.ContatoDTO;
import br.com.dbc.vemser.financeiro.service.ContatoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping("/contato")
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
public class ContatoController {

    private final ContatoService contatoService;

    @GetMapping("/lista")
    public ResponseEntity<List<ContatoDTO>> listarTodosContatos(){
        return null;
    }

    @GetMapping("/{idCliente}/cliente")
    public ResponseEntity<List<ContatoDTO>> listarTodosContatosDoCliente(@NotNull Integer idCliente){
        return null;
    }

    @PostMapping
    public ResponseEntity<ContatoDTO> criar(@RequestBody @Valid ContatoCreateDTO contato){
        log.info("Criando Contato!");
        log.info("Contato Criado!");
        return null;
    }

    @PutMapping("/{idContato}")
    public ResponseEntity<Void> atualizar(@NotNull Integer idContato,
                                                 @RequestBody @Valid ContatoCreateDTO contato){
        log.info("Atualizando Contato!");
        log.info("Contato Atualizado!");
        return null;
    }

    @DeleteMapping("/{idContato}")
    public ResponseEntity<ContatoDTO> deletar(@NotNull Integer idContato){
        log.info("Deletando Contato!");
        log.info("Contato Deletado!");
        return null;
    }
}
