package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.dto.CompraCreateDTO;
import br.com.dbc.vemser.financeiro.dto.CompraDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.model.Compra;
import br.com.dbc.vemser.financeiro.service.CompraService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping("/compra")
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
public class CompraController implements ControleListar<List<CompraDTO>>, ControleDeletar{

    private final CompraService compraService;

    // admin
<<<<<<< HEAD
    @Override
    @GetMapping("/lista")
    public ResponseEntity<List<CompraDTO>> listar(){
        return ResponseEntity.ok(this.compraService.listar());
=======
    @GetMapping("/listar")
    public ResponseEntity<List<Compra>> listarTodasCompras(@RequestHeader("login") String login,
                                                           @RequestHeader("senha") String senha) throws BancoDeDadosException, RegraDeNegocioException {
        return ResponseEntity.ok(compraService.list(login, senha));
>>>>>>> fde39426e9ac026ec890b618a8872914db662b7d
    }

    @GetMapping("/{numeroCartao}/cartao")
    public ResponseEntity<List<CompraDTO>> listarComprasDoCartao(@NotNull @PathVariable("numeroCartao") Long numeroCartao,
                                                                 @RequestHeader("numeroConta") Integer numeroConta,
                                                                 @RequestHeader("senha") String senha) throws BancoDeDadosException, RegraDeNegocioException {
        return ResponseEntity.ok(compraService.retornarComprasCartao(numeroCartao, numeroConta, senha));
    }

    @PostMapping("/{numeroCartao}/cartao")
    public ResponseEntity<CompraDTO> criar(@RequestBody @Valid CompraCreateDTO compra,
                                           @RequestHeader("numeroConta") Integer numeroConta,
                                           @RequestHeader("senha") String senha) throws BancoDeDadosException, RegraDeNegocioException {
        return ResponseEntity.ok(compraService.adicionar(compra, numeroConta, senha));
    }
}
