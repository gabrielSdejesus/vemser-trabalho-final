package br.com.dbc.vemser.financeiro.service;

import br.com.dbc.vemser.financeiro.dto.TransferenciaCreateDTO;
import br.com.dbc.vemser.financeiro.dto.TransferenciaDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.model.Transferencia;
import br.com.dbc.vemser.financeiro.repository.TransferenciaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TransferenciaService extends Servico {

    private final TransferenciaRepository transferenciaRepository;
    private final ContaService contaService;

    public TransferenciaService(TransferenciaRepository transferenciaRepository, ObjectMapper objectMapper, ContaService contaService) {
        super(objectMapper);
        this.transferenciaRepository = transferenciaRepository;
        this.contaService = contaService;
    }

    public TransferenciaDTO adicionarTransferencia(TransferenciaCreateDTO transferenciaCreateDTO, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        contaService.validandoAcessoConta(numeroConta, senha);
        Transferencia transferencia = objectMapper.convertValue(transferenciaCreateDTO, Transferencia.class);
        return objectMapper.convertValue(transferenciaRepository.adicionar(transferencia), TransferenciaDTO.class);
    }

    public TransferenciaDTO retornarTransferencia(Integer idTransferencia, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        contaService.validandoAcessoConta(numeroConta, senha);
        if(Objects.equals(transferenciaRepository.retornarTransferencia(idTransferencia).getContaEnviou(), Long.valueOf(contaService.retornarContaCliente(numeroConta, senha).getNumeroConta()))){
            return objectMapper.convertValue(transferenciaRepository.retornarTransferencia(idTransferencia), TransferenciaDTO.class);
        }else{
            throw new RegraDeNegocioException("Essa transferência não te pertence!");
        }
    }

    public List<TransferenciaDTO> listarTransferencias(String login, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        if (login.equals("admin") && senha.equals("abacaxi")) {
            return transferenciaRepository.listar().stream()
                    .map(transferencia -> objectMapper.convertValue(transferencia, TransferenciaDTO.class))
                    .toList();
        }else{
            throw new RegraDeNegocioException("Credenciais de Administrador inválidas!");
        }
    }

    public List<TransferenciaDTO> listarTransferenciasDaConta(Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        contaService.validandoAcessoConta(numeroConta, senha);
        return transferenciaRepository.listarTransferenciasPorConta(numeroConta).stream()
                .map(transferencia -> objectMapper.convertValue(transferencia, TransferenciaDTO.class))
                .collect(Collectors.toList());
    }

}
