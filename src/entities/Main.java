package entities;

import entities.exception.BancoDeDadosException;
import entities.model.Cliente;
import entities.model.Conta;
import entities.model.Contato;
import entities.model.Endereco;
import entities.repository.ContatoRepository;
import entities.repository.EnderecoRepository;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws BancoDeDadosException {

        EnderecoRepository ep = new EnderecoRepository();

        Cliente cliente = new Cliente();
        cliente.setNome("Josefina Da Silva");


        Endereco endereco = new Endereco();
        endereco.setCidade("Limeira");
        endereco.setCep("41555-222");
        endereco.setEstado("BA");
        endereco.setPais("Alemanha");
        endereco.setLogradouro("Rua Nada");
        ep.editar(5, endereco);
        List<Endereco> enderecos = ep.listar();
        enderecos.forEach(System.out::println);
    }
}
