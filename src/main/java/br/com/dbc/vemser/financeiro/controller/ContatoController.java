package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.dto.ContatoCreateDTO;
import br.com.dbc.vemser.financeiro.dto.ContatoDTO;
import br.com.dbc.vemser.financeiro.model.Contato;
import br.com.dbc.vemser.financeiro.service.ContatoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RequestMapping("/contato")
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
public class ContatoController {

    private final ContatoService contatoService;

    public ResponseEntity<ContatoDTO> listarTodosContatos(){
        return null;
    }

    public ResponseEntity<ContatoDTO> listarTodosContatosDoCliente(){
        return null;
    }

    public ResponseEntity<ContatoDTO> criar(@RequestBody @Valid ContatoCreateDTO contato){
        log.info("Criando Contato!");
        log.info("Contato Criado!");
        return null;
    }

    public ResponseEntity<ContatoDTO> atualizar(@NotNull Integer numeroConta,
                                                 @RequestBody @Valid ContatoCreateDTO contato){
        log.info("Atualizando Contato!");
        log.info("Contato Atualizado!");
        return null;
    }

    public ResponseEntity<ContatoDTO> deletar(@NotNull Integer numeroConta, @NotNull Integer contato){
        log.info("Deletando Contato!");
        log.info("Contato Deletado!");
        return null;
    }
}
