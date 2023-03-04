package br.com.dbc.vemser.financeiro.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import br.com.dbc.vemser.financeiro.controller.CartaoDTO;
import br.com.dbc.vemser.financeiro.dto.CartaoCreateDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.model.*;
import br.com.dbc.vemser.financeiro.repository.CartaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class CartaoService extends Servico {

    private final CartaoRepository cartaoRepository;

    public CartaoService(CartaoRepository cartaoRepository, ObjectMapper objectMapper) {
        super(objectMapper);
        this.cartaoRepository = cartaoRepository;
    }

    public List<CartaoDTO> listarPorIdConta(Integer numeroConta) {
        List<Cartao> cartoes = this.returnCartoes(conta);
        int index = 1;

        if (cartoes.size() > 0) {
            for(Cartao cartao: cartoes){
                if (cartao.getTipo() == tipo) {
                    System.err.println("\t\t\nExibindo dados do cartão [" + (index) + "]:");
                    StringBuilder string = new StringBuilder();
                    string.append("\tTipo do cartão: ").append((tipo == TipoCartao.DEBITO) ? "DÉBITO" : "CRÉDITO").append("\n");
                    string.append("\tNúmero da conta do cartão: ").append(cartao.getConta().getNumeroConta()).append("\n");
                    string.append("\tNúmero do cartão: ").append(cartao.getNumeroCartao()).append("\n");
                    string.append("\tCódigo de segurança do cartão: ").append(cartao.getCodigoSeguranca()).append("\n");
                    string.append("\tData de expedição: ").append(cartao.getDataExpedicao()).append("\n");

                    if(cartao.getTipo() == TipoCartao.CREDITO){
                        CartaoDeCredito cdc = (CartaoDeCredito) cartao;
                        string.append("\tLimite do cartão: 1000\n");
                        string.append("\tLimite disponível: ").append(cdc.getLimite()).append("\n");
                    }

                    Servico.tempoParaExibir(100);
                    System.out.println(string);
                    index++;
                }
            }
            if (index == 1) {
                System.err.println("Você não possui nenhum cartão de " + ((tipo == TipoCartao.DEBITO) ? "débito!" : "crédito!") + "\n");
            }
        }

        return null;
    }

    public CartaoDTO criar(Integer numeroConta, CartaoCreateDTO cartaoCreateDTO) {
        List<Cartao> cartoes = this.returnCartoes(conta);
        if (cartoes != null && cartoes.size() == 2) {
            System.err.println("Você não pode adicionar mais cartões, só é possível ter no máximo 2x cartões!\n");
            return null;
        } else if(tipoCartao == null){

            //Lendo e passando o valor referente ao tipo do cartão
            int valor = askInt("\nInsira o tipo do cartão:\n[1] Débito\n[2] Crédito\n[3] Cancelar");
            if (valor > 0 && valor < 3) {
                tipoCartao = TipoCartao.getTipoCartao(valor);
                System.out.println();
            } else {
                System.err.println("Operação cancelada!\n");
                return null;
            }
        }
            switch (tipoCartao) {
                case CREDITO -> {
                    CartaoDeCredito cartaoDeCredito = new CartaoDeCredito();
                    cartaoDeCredito.setConta(conta);
                    cartaoDeCredito.setLimite(1000);
                    cartaoDeCredito.setTipo(TipoCartao.CREDITO);
                    cartaoDeCredito.setVencimento(LocalDate.now().plusYears(4));
                    cartaoDeCredito.setCodigoSeguranca(ThreadLocalRandom.current().nextInt(100, 999));
                    cartaoDeCredito.setDataExpedicao(LocalDate.now());
                    try {
                        cartaoDeCredito = (CartaoDeCredito) this.cartaoRepository.adicionar(cartaoDeCredito);
                        if (cartaoDeCredito != null) {
                            System.err.println("Novo cartão de crédito adicionado com sucesso!");
                            System.out.println("\tDados do cartão: ");
                            System.out.println("\t\tNúmero da conta que possui o cartão: " + cartaoDeCredito.getConta().getNumeroConta());
                            System.out.println("\t\tLimite: " + cartaoDeCredito.getLimite());
                            System.out.println("\t\tTipo: " + cartaoDeCredito.getTipo());
                            System.out.println("\t\tVencimento: " + cartaoDeCredito.getVencimento());
                            System.out.println("\t\tData de expedição: " + cartaoDeCredito.getDataExpedicao());
                            System.out.println("\t\tCódigo de segurança: " + cartaoDeCredito.getCodigoSeguranca() + "\n");
                        }
                    } catch (BancoDeDadosException e) {
                        e.printStackTrace();
                    }
                }
                case DEBITO -> {
                    CartaoDeDebito cartaoDeDebito = new CartaoDeDebito();
                    cartaoDeDebito.setConta(conta);
                    cartaoDeDebito.setTipo(TipoCartao.DEBITO);
                    cartaoDeDebito.setCodigoSeguranca(ThreadLocalRandom.current().nextInt(100, 999));
                    cartaoDeDebito.setDataExpedicao(LocalDate.now());
                    cartaoDeDebito.setVencimento(LocalDate.now().plusYears(4));
                    try {
                        cartaoDeDebito = (CartaoDeDebito) this.cartaoRepository.adicionar(cartaoDeDebito);
                        if (cartaoDeDebito != null) {
                            System.out.println("Novo CARTÃO de DÉBITO adicionado com sucesso!");
                            System.out.println("\tDados do CARTÃO: ");
                            System.out.println("\t\tNúmero da conta que possui o cartão: " + cartaoDeDebito.getConta().getNumeroConta());
                            System.out.println("\t\tTipo: " + cartaoDeDebito.getTipo());
                            System.out.println("\t\tVencimento: " + cartaoDeDebito.getVencimento());
                            System.out.println("\t\tData de expedição: " + cartaoDeDebito.getDataExpedicao());
                            System.out.println("\t\tCódigo de segurança: " + cartaoDeDebito.getCodigoSeguranca() + "\n");
                        }
                    } catch (BancoDeDadosException e) {
                        e.printStackTrace();
                    }
                }
                default -> System.err.println("Erro bizarro!");
            }
        return null;
    }

    public CartaoDTO atualizar(Integer numeroCartao, CartaoCreateDTO cartaoCreateDTO) {
        return null;
    }

    public void deletar(Integer numeroCartao) {
        List<Cartao> cartoes = this.returnCartoes(conta);
        if (cartoes.size() == 1) {
            System.err.println("Você não pode remover mais cartões, é necessário ter no mínimo 1x cartão!\n");
        } else {
            int cartao;

            StringBuilder message = new StringBuilder("\nSelecione o cartão para remover:\n");
            for (int i = 0; i < cartoes.size(); i++) {
                if (cartoes.get(i) != null) {
                    message.append("Cartão [").append(i + 1).append("] -> ").append(cartoes.get(i).getTipo() == TipoCartao.DEBITO ? "Débito" : "Crédito");
                    //pular uma linha para exibição
                    if(i == 0){
                        message.append("\n");
                    }
                }
            }

            cartao = askInt(String.valueOf(message)) - 1;
            if (cartao > -1 && cartao < cartoes.size()) {
                try {
                    if (cartoes.get(cartao).getTipo() == TipoCartao.CREDITO) {
                        CartaoDeCredito cartaoDeCredito = (CartaoDeCredito) cartoes.get(cartao);
                        if (cartaoDeCredito.getLimite() == 1000 && this.cartaoRepository.remover(cartoes.get(cartao).getNumeroCartao())) {
                            System.err.println("Cartão removido com sucesso!\n");
                        } else {
                            System.err.println("Não é possível deletar o cartão!\n");
                        }
                    } else {
                        if (this.cartaoRepository.remover(cartoes.get(cartao).getNumeroCartao())) {
                            System.err.println("Cartão removido com sucesso!\n");
                        } else {
                            System.err.println("Não é possível deletar o cartão!\n");
                        }
                    }

                } catch (BancoDeDadosException e) {
                    e.printStackTrace();
                }
            } else if (cartao > 1){
                System.err.println("Opção inválida!\n");
            }
        }
    }


    //função exclusiva do administrador
    public void deletarCartao(Cartao cartao) {
        try {
            if (cartao.getTipo() == TipoCartao.CREDITO) {
                CartaoDeCredito cartaoDeCredito = (CartaoDeCredito) cartao;
                if (cartaoDeCredito.getLimite() == 1000 && this.cartaoRepository.remover(cartaoDeCredito.getNumeroCartao())) {
                    System.err.println("Cartão removido com sucesso!");
                } else {
                    System.err.println("ERR: Problemas na deleção do cartão: CRÉDITO!");
                }
            } else {
                if (this.cartaoRepository.remover(cartao.getNumeroCartao())) {
                    System.err.println("Cartão removido com sucesso!");
                } else {
                    System.err.println("ERR: Problemas na deleção do cartão: DÉBITO!");
                }
            }
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public List<Cartao> returnCartoes(Conta conta) {
        List<Cartao> cartoes = new ArrayList<>();
        try {
            cartoes = this.cartaoRepository.listarCartoesPorNumeroConta(conta);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
        if (cartoes.size() == 0) {
            return null;
        } else {
            return cartoes;
        }
    }

    public boolean editarCartao(String idCartao, Cartao cartao) {
        try {
            return this.cartaoRepository.editar(idCartao, cartao);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
        return false;
    }
}
