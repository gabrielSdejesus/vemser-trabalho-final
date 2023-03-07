package br.com.dbc.vemser.financeiro.service;

import br.com.dbc.vemser.financeiro.dto.ContaAcessDTO;
import br.com.dbc.vemser.financeiro.dto.TransferenciaCreateDTO;
import br.com.dbc.vemser.financeiro.dto.TransferenciaDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.model.Transferencia;
import br.com.dbc.vemser.financeiro.repository.TransferenciaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public TransferenciaDTO adicionarTransferencia(TransferenciaCreateDTO transferenciaCreateDTO, ContaAcessDTO contaAcessDTO) throws BancoDeDadosException, RegraDeNegocioException {
        contaService.validandoAcessoConta(contaAcessDTO);
        Transferencia transferencia = objectMapper.convertValue(transferenciaCreateDTO, Transferencia.class);
        return objectMapper.convertValue(this.transferenciaRepository.adicionar(transferencia), TransferenciaDTO.class);
    }

    public TransferenciaDTO retornarTransferencia(Integer idTransferencia) throws BancoDeDadosException, RegraDeNegocioException {
        return objectMapper.convertValue(this.transferenciaRepository.retornarTransferencia(idTransferencia), TransferenciaDTO.class);
    }

    public List<TransferenciaDTO> listarTransferencias() throws BancoDeDadosException, RegraDeNegocioException {
        return transferenciaRepository.listar().stream()
                .map(transferencia -> objectMapper.convertValue(transferencia, TransferenciaDTO.class))
                .collect(Collectors.toList());
    }

    public List<TransferenciaDTO> listarTransferenciasDaConta(Integer numeroConta, ContaAcessDTO contaAcessDTO) throws BancoDeDadosException, RegraDeNegocioException {
        contaService.validandoAcessoConta(contaAcessDTO);
        return transferenciaRepository.listarTransferenciasPorConta(numeroConta).stream()
                .map(transferencia -> objectMapper.convertValue(transferencia, TransferenciaDTO.class))
                .collect(Collectors.toList());
    }

}
