package br.com.dbc.vemser.financeiro.service;

import br.com.dbc.vemser.financeiro.dto.ContaCreateDTO;
import br.com.dbc.vemser.financeiro.dto.ContaDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.model.Conta;
import br.com.dbc.vemser.financeiro.model.Status;
import br.com.dbc.vemser.financeiro.repository.ContaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContaService extends Servico {
    private final ContaRepository contaRepository;
    private final ClienteService clienteService;
    private final CartaoService cartaoService;
    private final TransferenciaService transferenciaService;

    public ContaService(ContaRepository contaRepository, ClienteService clienteService, CartaoService cartaoService, TransferenciaService transferenciaService, ObjectMapper objectMapper) {
        super(objectMapper);
        this.contaRepository = contaRepository;
        this.clienteService = clienteService;
        this.cartaoService = cartaoService;
        this.transferenciaService = transferenciaService;
    }

    public ContaDTO adicionar(ContaCreateDTO contaCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        Conta conta = objectMapper.convertValue(contaCreateDTO, Conta.class);
        return objectMapper.convertValue(contaRepository.adicionar(conta), ContaDTO.class);
    }

    public List<ContaDTO> listar() throws BancoDeDadosException, RegraDeNegocioException {
        return contaRepository.listar().stream()
                .map(conta -> objectMapper.convertValue(conta, ContaDTO.class))
                .collect(Collectors.toList());
    }

    public ContaDTO editar(Integer numeroConta, ContaCreateDTO contaCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        Conta conta = objectMapper.convertValue(contaCreateDTO, Conta.class);
        return objectMapper.convertValue(contaRepository.editar(numeroConta, conta), ContaDTO.class);
    }

    //função exclusiva do administrador
    public boolean removerConta(Integer numeroConta) throws BancoDeDadosException, RegraDeNegocioException {
        return contaRepository.remover(numeroConta);
    }

    public ContaDTO retornarLoginConta(Integer numeroConta, String senhaConta) throws BancoDeDadosException, RegraDeNegocioException{
        Conta conta = retornarConta(numeroConta);
        if (conta != null && conta.getSenha() != null && conta.getSenha().equals(senhaConta) && conta.getStatus() == Status.ATIVO) {
            return objectMapper.convertValue(conta, ContaDTO.class);
        } else {
            //retorna null se não existe conta alguma ou algum dado está errado
            if (conta == null) {
                throw new RegraDeNegocioException("Número da conta ou Senha inválida!");
            } else if (conta.getStatus() == Status.INATIVO) {//a conta já existiu porém ela está inativa
                throw new RegraDeNegocioException("Conta inativa!");
            }
        }
        return objectMapper.convertValue(conta, ContaDTO.class);
    }

    private Conta retornarConta(Integer numeroConta) throws BancoDeDadosException, RegraDeNegocioException{
        return this.contaRepository.consultarPorNumeroConta(numeroConta);
    }

    public ContaDTO depositar(Integer numeroConta, Double valor) throws BancoDeDadosException, RegraDeNegocioException{
        Conta conta = retornarConta(numeroConta);
        if(conta != null){
            if(conta.getStatus() != Status.ATIVO){
                if(valor <= 0){
                    throw new RegraDeNegocioException("Valor de depósito inválido!");
                }else{
                    conta.setSaldo(conta.getSaldo()+valor);
                    return editar(numeroConta, objectMapper.convertValue(conta, ContaCreateDTO.class));
                }
            }else{
                throw new RegraDeNegocioException("Conta inativada!");
            }
        }else{
            throw new RegraDeNegocioException("Número da conta inválida!");
        }
    }

    public ContaDTO sacar(Integer numeroConta, Double valor) throws BancoDeDadosException, RegraDeNegocioException {
        Conta conta = retornarConta(numeroConta);
        if(conta != null){
            if(conta.getStatus() != Status.ATIVO){
                if(valor <= 0){
                    throw new RegraDeNegocioException("Valor de saque inválido!");
                }else{
                    if(conta.getSaldo()-valor < 0){
                        throw new RegraDeNegocioException("Saldo insuficiente para saque!");
                    }else{
                        conta.setSaldo(conta.getSaldo()-valor);
                        return editar(numeroConta, objectMapper.convertValue(conta, ContaCreateDTO.class));
                    }
                }
            }else{
                throw new RegraDeNegocioException("Conta inativada!");
            }
        }else{
            throw new RegraDeNegocioException("Número da conta inválida!");
        }
    }

    public boolean transferir(Integer numeroContaEnvia, Integer numeroContaRecebe, Double valor) throws BancoDeDadosException, RegraDeNegocioException {
        Conta recebe = retornarConta(numeroContaRecebe),
                envia = retornarConta(numeroContaEnvia);
        if(recebe != null && envia != null){
            if(recebe.getStatus() == Status.ATIVO && envia.getStatus() == Status.ATIVO){
                if(valor > 0){
                    if(envia.getSaldo()-valor >= 0){
                        envia.setSaldo(envia.getSaldo()-valor);
                        recebe.setSaldo(recebe.getSaldo()+valor);
                        editar(numeroContaEnvia, objectMapper.convertValue(envia, ContaCreateDTO.class));
                        editar(numeroContaRecebe, objectMapper.convertValue(recebe, ContaCreateDTO.class));
                        return true;
                    }else{
                        throw new RegraDeNegocioException("Saldo da conta que envia insuficiente!");
                    }
                }else{
                    throw new RegraDeNegocioException("Valor de transferência inválido!");
                }
            }else{
                if(recebe.getStatus() == Status.INATIVO){
                    throw new RegraDeNegocioException("Conta que recebe inativa!");
                }else{
                    throw new RegraDeNegocioException("Conta que envia inativa!");
                }
            }
        }else{
            if(recebe == null){
                throw new RegraDeNegocioException("Número da conta que recebe incorreto!");
            }else{
                throw new RegraDeNegocioException("Número da conta que envia incorreto!");
            }
        }
    }

    public ContaDTO pagar(Integer numeroConta, Double valor) throws BancoDeDadosException, RegraDeNegocioException {
        Conta conta = retornarConta(numeroConta);
        if(conta != null){
            if(conta.getStatus() != Status.ATIVO){
                if(valor <= 0){
                    throw new RegraDeNegocioException("Valor de pagamento inválido!");
                }else{
                    if(conta.getSaldo()-valor < 0){
                        throw new RegraDeNegocioException("Saldo insuficiente para pagar!");
                    }else{
                        conta.setSaldo(conta.getSaldo()-valor);
                        return editar(numeroConta, objectMapper.convertValue(conta, ContaCreateDTO.class));
                    }
                }
            }else{
                throw new RegraDeNegocioException("Conta inativada!");
            }
        }else{
            throw new RegraDeNegocioException("Número da conta inválida!");
        }
    }

    public ContaDTO alterarSenha(String senhaAntiga, String senhaNova, Integer numeroConta) throws BancoDeDadosException, RegraDeNegocioException {
        Conta conta = retornarConta(numeroConta);
        if(conta.getSenha().equals(senhaAntiga)){
            conta.setSenha(senhaNova);
            return editar(numeroConta, objectMapper.convertValue(conta, ContaCreateDTO.class));
        }else{
            throw new RegraDeNegocioException("Senha antiga incorreta! Caso não lembre sua senha para alterá-la, entre em contato com o administrador do banco!");
        }
    }

    public ContaDTO reativarConta(Integer numeroConta, String senhaAdmin) throws BancoDeDadosException, RegraDeNegocioException {
        Conta conta = retornarConta(numeroConta);
        if(senhaAdmin.equals("ABACAXI")){
            if(conta.getStatus()==Status.ATIVO){
                throw new RegraDeNegocioException("Conta já está ativa!");
            }else{
                conta.setStatus(Status.ATIVO);
                return editar(numeroConta, objectMapper.convertValue(conta, ContaCreateDTO.class));
            }
        }else{
            throw new RegraDeNegocioException("Senha administrativa incorreta!");
        }
    }
}
