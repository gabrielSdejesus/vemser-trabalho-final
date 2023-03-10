package br.com.dbc.vemser.financeiro.service;


import br.com.dbc.vemser.financeiro.dto.CartaoCreateDTO;
import br.com.dbc.vemser.financeiro.dto.CartaoDTO;
import br.com.dbc.vemser.financeiro.dto.CartaoPagarDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.model.Cartao;
import br.com.dbc.vemser.financeiro.model.CartaoDeCredito;
import br.com.dbc.vemser.financeiro.model.CartaoDeDebito;
import br.com.dbc.vemser.financeiro.model.TipoCartao;
import br.com.dbc.vemser.financeiro.repository.CartaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CartaoService extends Servico {

    private final CartaoRepository cartaoRepository;
    private final ContaService contaService;

    public CartaoService(@Lazy ContaService contaService, CartaoRepository cartaoRepository, ObjectMapper objectMapper) {
        super(objectMapper);
        this.cartaoRepository = cartaoRepository;
        this.contaService = contaService;
    }

    public List<CartaoDTO> listarPorNumeroConta(Integer numeroConta) throws BancoDeDadosException {
        List<Cartao> cartoes = cartaoRepository.listarPorNumeroConta(numeroConta);
        return cartoes.stream()
                .map(cartao -> objectMapper.convertValue(cartao, CartaoDTO.class))
                .collect(Collectors.toList());
    }

    public CartaoDTO criar(Integer numeroConta, String senha, TipoCartao tipo) throws BancoDeDadosException, RegraDeNegocioException {
        contaService.validandoAcessoConta(numeroConta, senha);
        List<Cartao> cartoes = cartaoRepository.listarPorNumeroConta(numeroConta);
        if (cartoes.size() == 2) {
            throw new RegraDeNegocioException("Usuário já possui dois cartões");
        } else {
            Cartao cartao;
            if (tipo.equals(TipoCartao.DEBITO)) {
                cartao = new CartaoDeDebito();
            } else {
                cartao = new CartaoDeCredito();
            }
            cartao.setNumeroConta(numeroConta);
            cartao.setDataExpedicao(LocalDate.now());
            cartao.setCodigoSeguranca(ThreadLocalRandom.current().nextInt(100, 999));
            cartao.setTipo(tipo);
            cartao.setVencimento(cartao.getDataExpedicao().plusYears(4));

            return objectMapper.convertValue(cartaoRepository.adicionar(cartao), CartaoDTO.class);
        }
    }

    public CartaoDTO pagar(CartaoDTO cartaoDTO, Double valor, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        //Validando acesso a conta
        contaService.validandoAcessoConta(numeroConta, senha);

        //Validando e retornando cartões da conta.
        Cartao cartao = validarCartao(cartaoDTO, numeroConta);

        /*********** CARTÃO DE CRÉDITO **********/

        //Validando e pegando cartão de crédito
        if (cartao instanceof CartaoDeCredito){
            CartaoDeCredito cartaoDeCredito = (CartaoDeCredito) cartao;

            //Verificando o limite
            if (cartaoDeCredito.getLimite() < valor) {
                throw new RegraDeNegocioException("Cartão de crédito não possui limite suficiente!");
            }
            //Pagando com o cartão de crédito
            cartaoDeCredito.setLimite(cartaoDeCredito.getLimite() - valor);
            Cartao cartaoAtualizado = cartaoRepository.editar(cartaoDeCredito.getNumeroCartao(), cartaoDeCredito);
            CartaoDTO cartaoDTOAtualizado = objectMapper.convertValue(cartaoAtualizado, CartaoDTO.class);
            cartaoDTOAtualizado.setarLimite(cartaoDeCredito.getLimite());
            return cartaoDTOAtualizado;
        }

        /*********** CARTÃO DE DÉBITO **********/

        //Validando e pagando com cartão de débito
        if (cartao instanceof CartaoDeDebito){
            CartaoDeDebito cartaoDeDebito = (CartaoDeDebito) cartao;
            contaService.sacar(valor, numeroConta, senha);
            return objectMapper.convertValue(cartaoDeDebito, CartaoDTO.class);
        }

        return null;
    }

    public CartaoDTO atualizar(Long numeroCartao, CartaoCreateDTO cartaoCreateDTO) throws RegraDeNegocioException, BancoDeDadosException {
        Cartao cartao = cartaoRepository.getPorNumeroCartao(numeroCartao);
        Cartao cartaoEditado;
        if(cartao.getTipo().equals(TipoCartao.DEBITO)) {
            cartaoEditado = cartaoRepository.editar(numeroCartao, objectMapper.convertValue(cartaoCreateDTO, CartaoDeDebito.class));
        } else {
            cartaoEditado = cartaoRepository.editar(numeroCartao, objectMapper.convertValue(cartaoCreateDTO, CartaoDeCredito.class));
        }
        return objectMapper.convertValue(cartaoEditado, CartaoDTO.class);
    }

    public void deletar(Long numeroCartao) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Buscando cartão...");
        Cartao cartao = cartaoRepository.getPorNumeroCartao(numeroCartao);
        List<Cartao> cartoes = cartaoRepository.listarPorNumeroConta(cartao.getNumeroConta());

        if (cartoes.size() == 1) {
            throw new RegraDeNegocioException("Cliente possui apenas um cartão");
        }

        //VALIDANDO CARTAO DE CRÉDITO
        if(cartao.getTipo().equals(TipoCartao.CREDITO)){
            CartaoDeCredito cartaoDeCredito = (CartaoDeCredito) cartao;
            if(cartaoDeCredito.getLimite() < 1000){
                throw new RegraDeNegocioException("Não é possível remover o cartão com limite em aberto!");
            }
        }

        cartaoRepository.remover(cartao.getNumeroCartao());
    }

    private Cartao validarCartao(CartaoDTO cartaoDTO, Integer numeroConta) throws BancoDeDadosException, RegraDeNegocioException {
        return cartaoRepository
                .listarPorNumeroConta(
                        numeroConta)
                .stream()
                .filter(cartao -> cartao.getNumeroCartao().equals(cartaoDTO.getNumeroCartao()))
                .filter(cartao -> cartao.getCodigoSeguranca().equals(cartaoDTO.getCodigoSeguranca()))
                .findFirst()
                .orElseThrow(()-> new RegraDeNegocioException("Dados do cartão inválido!"));
    }

    void deletarTodosCartoes(Integer numeroConta) throws BancoDeDadosException {
        List<CartaoDTO> cartoes = listarPorNumeroConta(numeroConta);

        for(CartaoDTO cartao: cartoes){
            cartaoRepository.remover(cartao.getNumeroCartao());
        }
    }

}