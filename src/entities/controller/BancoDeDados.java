package entities.controller;

import entities.interfaces.Exibicao;
import entities.model.Cliente;
import entities.model.Conta;

import java.util.ArrayList;
import java.util.List;

public class BancoDeDados implements Exibicao {

    private static List<Conta> contas = new ArrayList<>();

    public static void adicionarConta(Conta conta){
        contas.add(conta);
    }

    public static void alterarDadosDaConta(Conta conta){

        int controlador = -1;
        for(Conta x: contas){
            controlador++;
            if(x.getCliente().getCpf().equals(conta.getCliente().getCpf())) {
                contas.set(controlador, conta);
            }
        }
    }

    public static boolean consultarExistenciaPorCPF(Cliente cliente){
        return contas.stream().anyMatch(conta -> conta.getCliente().getCpf().equals(cliente.getCpf()));
    }

    public static boolean consultarNumeroDaConta(int numero){
        return contas.stream().anyMatch(conta -> conta.getNumero() == numero);
    }

    public static Conta consultarNumeroDaConta(String numero){
        int valor = Integer.parseInt(numero);
        return contas.stream().filter(conta -> conta.getNumero() == valor).findFirst().orElse(null);
    }

    public static boolean consultarNumeroDoCartao(int numero){
        return contas.stream().anyMatch(conta -> conta.getNumero() == numero);
    }

    @Override
    public void exibir() {

    }

    public static List<Conta> getContas() {
        return contas;
    }
}
