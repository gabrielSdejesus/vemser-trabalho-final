package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.dto.EnderecoCreateDTO;
import br.com.dbc.vemser.financeiro.dto.EnderecoDTO;
import br.com.dbc.vemser.financeiro.dto.TransferenciaCreateDTO;
import br.com.dbc.vemser.financeiro.dto.TransferenciaDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/endereco")
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
public class EnderecoController implements ControleListar<List<EnderecoDTO>>,
        ControleAdicionar<EnderecoCreateDTO, EnderecoDTO>,
        ControleListarPorID<EnderecoDTO>,
        ControleDeletar{

    private final EnderecoService enderecoService;

    @GetMapping("/{idCliente}/cliente")
    public ResponseEntity<List<EnderecoDTO>> listarTodosEnderecosDoCliente(@PathVariable("idCliente") Integer idCliente) throws BancoDeDadosException, RegraDeNegocioException {
        return ResponseEntity.ok(enderecoService.listarEnderecosDoCliente(idCliente));
    }

    @PutMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> atualizar(@PathVariable("idEndereco") Integer idEndereco,
                                 @RequestBody @Valid EnderecoCreateDTO endereco) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Atualizando Endereço!");
        EnderecoDTO enderecoDTO = enderecoService.atualizar(idEndereco, endereco);
        log.info("Endereço Atualizado!");
        return ResponseEntity.ok(enderecoDTO);
    }

    /////////
    @Override
    @GetMapping("/lista")
    public ResponseEntity<List<EnderecoDTO>> listar(String login, String senha) throws BancoDeDadosException, RegraDeNegocioException {//Função do ADM
        return ResponseEntity.ok(enderecoService.listarEnderecos(login, senha));
    }

    @Override
    public ResponseEntity<EnderecoDTO> listarPorId(Integer id, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        return ResponseEntity.ok(enderecoService.retornarEndereco(id, numeroConta, senha));
    }

    @Override
    public ResponseEntity<Boolean> deletar(Integer id, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Deletando Endereço!");
        Boolean deletado = enderecoService.deletar(id, numeroConta, senha);
        log.info("Endereço Deletado!");
        return ResponseEntity.ok(deletado);
    }

    @Override
    public ResponseEntity<EnderecoDTO> adicionar(EnderecoCreateDTO dado,
                                                      Integer numeroConta,
                                                      String senha) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Criando Endereço!");
        EnderecoDTO endereDTO = enderecoService.adicionar(dado, numeroConta, senha);
        log.info("Endereço Criado!");
        return ResponseEntity.ok(endereDTO);
    }
}
