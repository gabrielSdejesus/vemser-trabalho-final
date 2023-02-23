package entities;

import entities.exception.BancoDeDadosException;
import entities.model.Cliente;
import entities.model.Conta;
import entities.model.Contato;
import entities.model.Endereco;
import entities.repository.EnderecoRepository;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws BancoDeDadosException {
        /*ArrayList<Contato> contatos = new ArrayList<>();
        contatos.add(new Contato("1999990000", "nicolascanova@hotmail.com"));

        ArrayList<Endereco> enderecos = new ArrayList<>();
        enderecos.add(new Endereco("Rua do abacaxi", "Cidade do abacaxi", "Estado do abacaxi", "País do abacaxi", "Cep do abacaxi"));

        Cliente canova = new Cliente("Canova", "1234", enderecos, contatos, "123");

        Conta contaDoCanova = new Conta(canova, 50000, "123");

        System.out.println("Número da conta: "+contaDoCanova.getNumero());

        TelaPrincipal.exibirTelaPrincipal();*/

        EnderecoRepository ep = new EnderecoRepository();

        List<Endereco> enderecos = ep.listarEnderecosPorPessoa(1);

        enderecos.forEach(System.out::println);
    }
}
