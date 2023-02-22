package entities.service;

import entities.model.Conta;

public class TransferenciaService extends Service{

    public static void exibirTransferencias(Conta conta){
        conta.exibirTransferencias();
    }
}
