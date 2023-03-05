package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.dto.EnderecoCreateDTO;
import br.com.dbc.vemser.financeiro.dto.EnderecoDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
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
    public ResponseEntity<List<EnderecoDTO>> listarTodosEnderecos() throws BancoDeDadosException, RegraDeNegocioException {
        return ResponseEntity.ok(enderecoService.listarEnderecos());
    }

    @GetMapping("/{idCliente}/cliente")
    public ResponseEntity<List<EnderecoDTO>> listarTodosEnderecosDoCliente(@PathVariable("idCliente") Integer idCliente) throws BancoDeDadosException, RegraDeNegocioException {
        return ResponseEntity.ok(enderecoService.listarEnderecosDoCliente(idCliente));
    }

    @GetMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> retornarEndereco(@PathVariable("idEndereco") Integer idEndereco) throws BancoDeDadosException, RegraDeNegocioException {
        return ResponseEntity.ok(enderecoService.retornarEndereco(idEndereco));
    }

    @PostMapping
    public ResponseEntity<EnderecoDTO> criar(@RequestBody @Valid EnderecoCreateDTO endereco) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Criando Endereço!");
        EnderecoDTO endereDTO = enderecoService.adicionar(endereco);
        log.info("Endereço Criado!");
        return ResponseEntity.ok(endereDTO);
    }

    @PutMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> atualizar(@PathVariable("idEndereco") Integer idEndereco,
                                 @RequestBody @Valid EnderecoCreateDTO endereco) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Atualizando Endereço!");
        EnderecoDTO enderecoDTO = enderecoService.atualizar(idEndereco, endereco);
        log.info("Endereço Atualizado!");
        return ResponseEntity.ok(enderecoDTO);
    }

    @DeleteMapping("/{idEndereco}")
    public ResponseEntity<Void> deletar(
            @PathVariable("idEndereco") Integer idEndereco) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Deletando Endereço!");
        enderecoService.deletar(idEndereco);
        log.info("Endereço Deletado!");
        return ResponseEntity.ok().build();
    }
}
