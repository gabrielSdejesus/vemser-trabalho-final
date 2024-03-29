package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.dto.ClienteCreateDTO;
import br.com.dbc.vemser.financeiro.dto.ClienteDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/cliente")
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping("/lista")
    public ResponseEntity<List<ClienteDTO>> listarClientes() throws BancoDeDadosException {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> exibirCliente(@PathVariable("idCliente") Integer idCliente) throws BancoDeDadosException, RegraDeNegocioException {
        return ResponseEntity.ok(clienteService.visualizarCliente(idCliente));
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> atualizar(@PathVariable("idCliente") Integer idCliente,
                                                @RequestBody @Valid ClienteCreateDTO cliente) throws BancoDeDadosException {
        log.info("Atualizando Cliente!");
        ClienteDTO clienteDTO = clienteService.alterarCliente(idCliente, cliente);
        log.info("Cliente Atualizado!");
        return ResponseEntity.ok(clienteDTO);
    }
}
