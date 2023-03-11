package br.com.dbc.vemser.financeiro.controller;

import br.com.dbc.vemser.financeiro.dto.EnderecoCreateDTO;
import br.com.dbc.vemser.financeiro.dto.EnderecoDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/endereco")
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
public class EnderecoController implements ControleListar<List<EnderecoDTO>>, ControleAdicionar<EnderecoCreateDTO, EnderecoDTO>, ControleDeletar, ControleAtualizar<EnderecoCreateDTO, EnderecoDTO>{

    private final EnderecoService enderecoService;

    @Operation(summary = "Retorna os endereços do cliente", description = "Retorna os endereços do cliente do Banco de Dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Endereços retornados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/cliente")
    public ResponseEntity<List<EnderecoDTO>> listarEnderecosDoCliente(@RequestHeader("numeroConta") Integer numeroConta,
                                                                      @RequestHeader("senha") String senha) throws BancoDeDadosException, RegraDeNegocioException {
        return ResponseEntity.ok(enderecoService.listarEnderecosDoCliente(numeroConta, senha));
    }

    @Override
    @GetMapping("/lista")
    @Operation(summary = "FUNÇÃO ADM", description = "LISTAR TODOS OS ENDEREÇOS DO BANCO")
    public ResponseEntity<List<EnderecoDTO>> listar(String login, String senha) throws BancoDeDadosException, RegraDeNegocioException {//Função do ADM
        return ResponseEntity.ok(enderecoService.listarEnderecos(login, senha));
    }

    @Override
    public ResponseEntity<Boolean> deletar(Integer id, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Deletando Endereço!");
        Boolean deletado = enderecoService.deletar(id, numeroConta, senha);
        log.info("Endereço Deletado!");
        return ResponseEntity.ok(deletado);
    }

    @Override
    public ResponseEntity<EnderecoDTO> adicionar(EnderecoCreateDTO dado, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Criando Endereço!");
        EnderecoDTO endereDTO = enderecoService.adicionar(dado, numeroConta, senha);
        log.info("Endereço Criado!");
        return ResponseEntity.ok(endereDTO);
    }

    @Override
    public ResponseEntity<EnderecoDTO> atualizar(EnderecoCreateDTO dado, Integer id, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Atualizando Endereço!");
        EnderecoDTO enderecoDTO = enderecoService.atualizar(id, dado, numeroConta, senha);
        log.info("Endereço Atualizado!");
        return ResponseEntity.ok(enderecoDTO);
    }
}
