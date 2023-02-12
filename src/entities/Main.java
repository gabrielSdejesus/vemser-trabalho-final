package entities;

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
                ,enderecos,contatos,"GABRIEL","123");
        Conta conta = new Conta(cliente, 5000);
        System.out.println("Número da conta: " + conta.getNumero());
        TelaPrincipal.exibirTelaPrincipal();
    }
}
