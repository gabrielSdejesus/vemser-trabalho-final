package br.com.dbc.vemser.financeiro.controller;

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
import java.util.List;

@RequestMapping("/contato")
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
public class ContatoController implements ControleAdicionar<ContatoCreateDTO, ContatoDTO>, ControleListar<List<ContatoDTO>>, ControleListarPorID<ContatoDTO>,
        ControleDeletar{

    private final ContatoService contatoService;

    @GetMapping("/{idCliente}/cliente")
    public ResponseEntity<List<ContatoDTO>> listarContatosDoCliente(
            @PathVariable("idCliente") Integer idCliente) throws BancoDeDadosException, RegraDeNegocioException {
        return new ResponseEntity<>(contatoService.listarContatosDoCliente(idCliente), HttpStatus.OK);
    }

    @PutMapping("/{idContato}")
    public ResponseEntity<ContatoDTO> atualizar(@PathVariable Integer idContato,
                                                 @RequestBody @Valid ContatoCreateDTO contato) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Atualizando Contato!");
        ContatoDTO contatoDTO = contatoService.atualizar(idContato, contato);
        log.info("Contato Atualizado!");
        return ResponseEntity.ok(contatoDTO);
    }

    /////////
    @Override
    public ResponseEntity<ContatoDTO> listarPorId(Integer id, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        return ResponseEntity.ok(contatoService.retornarContato(id, numeroConta, senha));
    }

    @Override
    @GetMapping("/lista")
    public ResponseEntity<List<ContatoDTO>> listar(String login, String senha) throws BancoDeDadosException, RegraDeNegocioException {//Função do ADM
        return ResponseEntity.ok(contatoService.listarContatos(login, senha));
    }

    @Override
    public ResponseEntity<Boolean> deletar(Integer id, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Deletando Contato!");
        Boolean deletado = contatoService.deletar(id, numeroConta, senha);
        log.info("Contato Deletado!");
        return ResponseEntity.ok(deletado);
    }

    @Override
    public ResponseEntity<ContatoDTO> adicionar(ContatoCreateDTO dado,
                                                Integer numeroConta,
                                                String senha) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Criando Contato!");
        ContatoDTO contatoDTO = contatoService.adicionar(dado, numeroConta, senha);
        log.info("Contato Criado!");
        return ResponseEntity.ok(contatoDTO);
    }
}
