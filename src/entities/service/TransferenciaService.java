package entities.service;

import entities.exception.BancoDeDadosException;
import entities.model.Conta;
import entities.model.Transferencia;
import entities.repository.TransferenciaRepository;

import java.sql.SQLException;
import java.util.InputMismatchException;

public class TransferenciaService extends Service{

    private TransferenciaRepository transferenciaRepository;

    public TransferenciaService() {
        this.transferenciaRepository = new TransferenciaRepository();
    }

    public void adicionarTransferencia(Conta conta){
        Transferencia transferencia = new Transferencia();
        try{
            transferencia.setValor(askDouble("Insira o valor: "));

            transferencia.setContaEnviou(conta);

            Conta contaRecebeu = new Conta();
            contaRecebeu.setNumeroConta(askInt("Insira o número da conta que receberá o valor: "));
            transferencia.setContaRecebeu(contaRecebeu);

            transferencia = transferenciaRepository.adicionar(transferencia);
            if(transferencia != null){
                System.out.println("Transferência realizada com sucesso!");
                System.out.println("\tDados da transferência:");
                System.out.println("\t\tId da transferência: "+transferencia.getIdTransferencia());
                System.out.println("\t\tValor da transferência: "+transferencia.getValor());
                System.out.println("\t\tNúmero da conta que enviou: "+transferencia.getContaEnviou().getNumeroConta());
                System.out.println("\t\tNúmero da conta que recebeu: "+transferencia.getContaRecebeu().getNumeroConta());
            }else{
                System.err.println("Erro ao concluir transferência!");
            }
        }catch(BancoDeDadosException e){
            e.printStackTrace();
        }
    }

    public boolean editarTransferencia(Integer idTransferencia, Transferencia transferencia){
        try{
            return transferenciaRepository.editar(idTransferencia, transferencia);
        }catch(BancoDeDadosException e){
            e.printStackTrace();
        }
        return false;
    }

    public void removerTransferencia(Transferencia transferencia){
        try{
            transferenciaRepository.remover(transferencia.getIdTransferencia());
        }catch(BancoDeDadosException e){
            e.printStackTrace();
        }
    }

    public void listarTransferencias() {
        try {
            transferenciaRepository.listar().forEach(System.out::println);
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
