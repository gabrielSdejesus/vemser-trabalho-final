package entities.service;

import entities.exception.BancoDeDadosException;
import entities.repository.TransferenciaRepository;

public class TransferenciaService {

    private TransferenciaRepository transferenciaRepository;

    public TransferenciaService() {
        this.transferenciaRepository = new TransferenciaRepository();
    }

    public void listar() {
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
