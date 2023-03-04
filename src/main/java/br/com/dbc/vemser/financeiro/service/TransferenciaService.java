package br.com.dbc.vemser.financeiro.service;

import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.model.Conta;
import br.com.dbc.vemser.financeiro.model.Transferencia;
import br.com.dbc.vemser.financeiro.repository.TransferenciaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferenciaService extends Servico {

    private final TransferenciaRepository transferenciaRepository;

    public TransferenciaService(TransferenciaRepository transferenciaRepository, ObjectMapper objectMapper) {
        super(objectMapper);
        this.transferenciaRepository = transferenciaRepository;
    }

    public void adicionarTransferencia(Conta contaEnviou, Conta contaRecebeu, double valor){
        Transferencia transferencia = new Transferencia();
        try{
            transferencia.setValor(valor);
            if(transferencia.getValor() > 0){
                transferencia.setContaEnviou(contaEnviou);
                if(contaRecebeu.getNumeroConta() > 0){
                    transferencia.setContaRecebeu(contaRecebeu);

                    transferencia = transferenciaRepository.adicionar(transferencia);
                    if(transferencia != null){
                        System.err.println("\nTransferência realizada com sucesso!");
                        System.out.println("\tDados da transferência:");
                        System.out.println("\t\tValor da transferência: "+ transferencia.getValor());
                        System.out.println("\t\tNº da conta que enviou: "+ transferencia.getContaEnviou().getNumeroConta());
                        System.out.println("\t\tNº da conta que recebeu: "+ transferencia.getContaRecebeu().getNumeroConta() + "\n") ;
                    }else{
                        System.err.println("\nErro ao concluir transferência!");
                    }
                }else{
                    System.err.println("\nNúmero da conta inválido!");
                }
            }else{
                System.err.println("\nValor inválido!");
            }
        }catch(BancoDeDadosException e){
            e.printStackTrace();
        }
    }

    public void listarTransferencias() {
        try {
            List<Transferencia> transferencias = transferenciaRepository.listar();
            transferencias.forEach(System.out::println);
            if(transferencias.size() == 0){
                System.err.println("\nNenhuma TRANSFERÊNCIA realizada no Banco De Dados!");
            }
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void listarTransferenciasPorConta(Integer numeroConta) {
        try {
            transferenciaRepository.listarTransferenciasPorConta(numeroConta).forEach(System.out::println);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

}
