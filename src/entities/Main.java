package entities;

import entities.model.Cliente;
import entities.model.Conta;
import entities.model.Contato;
import entities.model.Endereco;
import entities.view.TelaPrincipal;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Contato> contatos = new ArrayList<>();
        contatos.add(new Contato("1999990000", "nicolascanova@hotmail.com"));

        ArrayList<Endereco> enderecos = new ArrayList<>();
        enderecos.add(new Endereco("Rua do abacaxi", "Cidade do abacaxi", "Estado do abacaxi", "País do abacaxi", "Cep do abacaxi"));

        Cliente canova = new Cliente("Canova", "1234", enderecos, contatos, "123");

        Conta contaDoCanova = new Conta(canova, 50000, "123");

        System.out.println("Número da conta: "+contaDoCanova.getNumero());

        TelaPrincipal.exibirTelaPrincipal();
    }
}
