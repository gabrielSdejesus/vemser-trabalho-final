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

import java.util.List;
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

    public CartaoDTO criar(Integer numeroConta, CartaoCreateDTO cartaoCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        List<Cartao> cartoes = cartaoRepository.listarPorNumeroConta(numeroConta);
        if (cartoes.size() == 2) {
            throw new RegraDeNegocioException("Usuário já possui dois cartões");
        } else {
            Cartao cartao;
            if (cartaoCreateDTO.getTipo().equals(TipoCartao.DEBITO)) {
                cartao = objectMapper.convertValue(cartaoCreateDTO, CartaoDeDebito.class);
            } else {
                cartao = objectMapper.convertValue(cartaoCreateDTO, CartaoDeCredito.class);
            }
            cartao.setNumeroConta(numeroConta);
            Cartao cartaoCriado = cartaoRepository.adicionar(cartao);
            return objectMapper.convertValue(cartaoCriado, CartaoDTO.class);
        }
    }

    public CartaoDTO pagar(CartaoPagarDTO cartaoPagarDTO, Integer numeroConta, String senha) throws BancoDeDadosException, RegraDeNegocioException {
        //Validando acesso a conta
        contaService.validandoAcessoConta(numeroConta, senha);

        //Validando e retornando cartões da conta.
        List<Cartao> cartoes = validarCartao(cartaoPagarDTO, numeroConta);

        /*********** CARTÃO DE CRÉDITO **********/

        //Validando e pagando com cartão de crédito
        if (cartaoPagarDTO.getTipoCartao().equals(TipoCartao.CREDITO)) {
            //Validando e pegando cartão de crédito
            if (cartoes.get(0) instanceof CartaoDeCredito){
                CartaoDeCredito cartaoDeCredito = (CartaoDeCredito) cartoes.stream().findFirst().get();

            //Verificando o limite
            if (cartaoDeCredito.getLimite() < cartaoPagarDTO.getValor()) {
                throw new RegraDeNegocioException("Cartão de crédito não possui limite suficiente!");
            }

            //Pagando com o cartão de crédito
            cartaoDeCredito.setLimite(cartaoDeCredito.getLimite() - cartaoPagarDTO.getValor());
            Cartao cartao = cartaoRepository.editar(cartaoDeCredito.getNumeroCartao(), cartaoDeCredito);
            CartaoDTO cartaoDTO = objectMapper.convertValue(cartao, CartaoDTO.class);
            cartaoDTO.setarLimite(cartaoDeCredito.getLimite());
            return cartaoDTO;
            }
        }

        /*********** CARTÃO DE DÉBITO **********/

        //Validando e pagando com cartão de débito
        CartaoDeDebito cartaoDeDebito = null;
        if (cartoes.get(0) instanceof CartaoDeDebito){
            cartaoDeDebito = (CartaoDeDebito) cartoes.stream().findFirst().get();
        }
        //Pagando com o cartão de débito
        contaService.sacar(cartaoPagarDTO.getValor(), numeroConta, senha);
        return objectMapper.convertValue(cartaoDeDebito, CartaoDTO.class);
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

    private List<Cartao> validarCartao(CartaoPagarDTO cartaoPagarDTO, Integer numeroConta) throws BancoDeDadosException, RegraDeNegocioException {
        List<Cartao> cartoesValidados = cartaoRepository
                .listarPorNumeroConta(
                        numeroConta)
                .stream()
                .filter(cartao -> cartao.getNumeroCartao().equals(cartaoPagarDTO.getNumeroCartao()))
                .filter(cartao -> cartao.getCodigoSeguranca().equals(cartaoPagarDTO.getCodigoSeguranca()))
                .filter(cartao -> cartao.getTipo().equals(cartaoPagarDTO.getTipoCartao()))
                .collect(Collectors.toList());

        if(cartoesValidados.isEmpty()){
            throw new RegraDeNegocioException("Dados do cartão inválido!");
        }

        return cartoesValidados;
    }

    void deletarTodosCartoes(Integer numeroConta) throws BancoDeDadosException {
        List<CartaoDTO> cartoes = listarPorNumeroConta(numeroConta);

        for(CartaoDTO cartao: cartoes){
            cartaoRepository.remover(cartao.getNumeroCartao());
        }
    }

}