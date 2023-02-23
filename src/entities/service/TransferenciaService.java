package entities.service;

import entities.model.Conta;
import entities.repository.TransferenciaRepository;

public class TransferenciaService extends Service{
    private TransferenciaRepository transferenciaRepository;

    public TransferenciaService() {
        this.transferenciaRepository = new TransferenciaRepository();
    }

    /*public static void exibirTransferencias(Conta conta){
        conta.exibirTransferencias();
    }*/
}
