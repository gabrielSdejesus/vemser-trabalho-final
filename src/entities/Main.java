package entities;

import entities.controller.BancoDeDados;
import entities.model.Cliente;
import entities.model.Conta;
import entities.model.Contato;
import entities.model.Endereco;
import entities.view.TelaPrincipal;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Endereco> enderecos = new ArrayList<>();
        enderecos.add(new Endereco("Rua Betel","Salvador","Bahia","Brasil","41630111"));
        ArrayList<Contato> contatos = new ArrayList<>();
        contatos.add(new Contato("7165478932","GABRIEL@GMAIL.COM"));
        Cliente cliente = new Cliente("Gabriel","07800145612"
                ,enderecos,contatos,"123");
        Conta conta = new Conta(cliente, 5000, "123");


        ArrayList<Endereco> enderecos2 = new ArrayList<>();
        enderecos2.add(new Endereco("Rua Betel","Salvador","Bahia","Brasil","41630111"));
        ArrayList<Contato> contatos2 = new ArrayList<>();
        contatos2.add(new Contato("7165478932","GABRIEL@GMAIL.COM"));
        Cliente cliente2 = new Cliente("Gabriel","11111"
                ,enderecos2,contatos2,"123");
        Conta conta2 = new Conta(cliente2, 5000, "123");

        System.out.println("Número da conta: " + conta.getNumero());
        System.out.println("Número da conta2: " + conta2.getNumero());
        TelaPrincipal.exibirTelaPrincipal();
    }
}
