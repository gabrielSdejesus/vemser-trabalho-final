package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.dto.EnderecoCreateDTO;
import br.com.dbc.vemser.financeiro.dto.EnderecoDTO;
import br.com.dbc.vemser.financeiro.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping("/endereco")
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoService enderecoService;

    @GetMapping("/lista")
    public ResponseEntity<List<EnderecoDTO>> listarTodosEnderecos(){
        return null;
    }

    @GetMapping("/{idCliente}/cliente")
    public ResponseEntity<List<EnderecoDTO>> listarTodosEnderecosDoCliente(@NotNull Integer idCliente){
        return null;
    }

    @PostMapping
    public ResponseEntity<EnderecoDTO> criar(@RequestBody @Valid EnderecoCreateDTO endereco){
        log.info("Criando Endereço!");
        log.info("Endereço Criado!");
        return null;
    }

    @PutMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> atualizar(@NotNull Integer idEndereco,
                                 @RequestBody @Valid EnderecoCreateDTO endereco){
        log.info("Atualizando Endereço!");
        log.info("Endereço Atualizado!");
        return null;
    }

    @DeleteMapping("/{idEndereco}")
    public ResponseEntity<Void> deletar(@NotNull Integer idEndereco){
        log.info("Deletando Endereço!");
        log.info("Endereço Deletado!");
        return null;
    }
}
