package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.dto.EnderecoCreateDTO;
import br.com.dbc.vemser.financeiro.dto.EnderecoDTO;
import br.com.dbc.vemser.financeiro.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RequestMapping("/endereco")
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoService enderecoService;

    public ResponseEntity<EnderecoDTO> listarTodosEnderecos(){
        return null;
    }

    public ResponseEntity<EnderecoDTO> listarTodosEnderecosDoCliente(){
        return null;
    }

    public ResponseEntity<EnderecoDTO> criar(@RequestBody @Valid EnderecoCreateDTO endereco){
        log.info("Criando Endereço!");
        log.info("Endereço Criado!");
        return null;
    }

    public ResponseEntity<EnderecoDTO> atualizar(@NotNull Integer numeroConta,
                                 @RequestBody @Valid EnderecoCreateDTO endereco){
        log.info("Atualizando Endereço!");
        log.info("Endereço Atualizado!");
        return null;
    }

    public ResponseEntity<EnderecoDTO> deletar(@NotNull Integer numeroConta, @NotNull Integer idEndereco){
        log.info("Deletando Endereço!");
        log.info("Endereço Deletado!");
        return null;
    }
}
