package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.dto.ClienteDTO;
import br.com.dbc.vemser.financeiro.dto.ContatoCreateDTO;
import br.com.dbc.vemser.financeiro.dto.ContatoDTO;
import br.com.dbc.vemser.financeiro.dto.EnderecoDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.service.ContatoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<ContatoDTO>> listarContatos() throws BancoDeDadosException {
        return ResponseEntity.ok(contatoService.listarContatos());
    }

    @GetMapping("/{idCliente}/cliente")
    public ResponseEntity<List<ContatoDTO>> listarContatosDoCliente(
            @PathVariable("idCliente") Integer idCliente) throws BancoDeDadosException, RegraDeNegocioException {
        return new ResponseEntity<>(contatoService.listarContatosDoCliente(idCliente), HttpStatus.OK);
    }

    @GetMapping("/{idContato}")
    public ResponseEntity<ContatoDTO> retornarEndereco(@PathVariable("idContato") Integer idContato) throws BancoDeDadosException, RegraDeNegocioException {
        return ResponseEntity.ok(contatoService.retornarContato(idContato));
    }

    @PostMapping
    public ResponseEntity<ContatoDTO> criar(@RequestBody @Valid ContatoCreateDTO contato) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Criando Contato!");
        ContatoDTO contatoDTO = contatoService.adicionar(contato);
        log.info("Contato Criado!");
        return ResponseEntity.ok(contatoDTO);
    }

    @PutMapping("/{idContato}")
    public ResponseEntity<ContatoDTO> atualizar(@PathVariable Integer idContato,
                                                 @RequestBody @Valid ContatoCreateDTO contato) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Atualizando Contato!");
        ContatoDTO contatoDTO = contatoService.atualizar(idContato, contato);
        log.info("Contato Atualizado!");
        return ResponseEntity.ok(contatoDTO);
    }

    @DeleteMapping("/{idContato}")
    public ResponseEntity<Void> deletar(@PathVariable("idContato") Integer idContato) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Deletando Contato!");
        contatoService.deletar(idContato);
        log.info("Contato Deletado!");
        return ResponseEntity.ok().build();
    }
}
