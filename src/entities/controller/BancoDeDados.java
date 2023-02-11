package entities.controller;

import entities.model.Cliente;
import entities.model.Conta;

import java.util.ArrayList;
import java.util.List;

public class BancoDeDados {

    private List<Conta> contas = new ArrayList<>();

    public void adicionarConta(Conta conta){
        contas.add(conta);
    }

    public void alterarDadosDaConta(Conta conta){

        int controlador = -1;
        for(Conta x: contas){
            controlador++;
            if(x.getCliente().getCpf().equals(conta.getCliente().getCpf())) {
                contas.set(controlador, conta);
            }
        }
    }

    public boolean consultarExistenciaPorCPF(Cliente cliente){

        for(Conta x: contas){
            if(x.getCliente().getCpf().equals(cliente.getCpf())) {
                return true;
            }
        }
        return false;
    }

    public boolean consultarNumeroDeConta(int numero){

        for(Conta x: contas){
            if(x.getNumero() == numero) {
                return false;
            }
        }
        return true;
    }
}
