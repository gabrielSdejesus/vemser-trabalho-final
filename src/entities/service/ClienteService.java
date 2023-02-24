package entities.service;

import entities.exception.BancoDeDadosException;
import entities.model.Cliente;
import entities.model.Conta;
import entities.model.Contato;
import entities.model.Endereco;
import entities.repository.ClienteRepository;
import entities.view.TelaAdministrador;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ClienteService extends Service{
    private ClienteRepository clienteRepository;

    public ClienteService() {
        this.clienteRepository = new ClienteRepository();
    }

    public Cliente adicionar(){
        String nomeCliente, cpfCliente;

        while (true) {
            System.out.print("Insira o nome do cliente: ");
            nomeCliente = SCANNER.nextLine().strip().toUpperCase();
            if (Pattern.matches("[0-9!@#$%^&*(),.?\":{}|<>]", nomeCliente)) {
                System.out.println("Nome inválido! Insira novamente.");
            } else break;
        }

        while (true) {
            System.out.print("Insira o CPF do cliente: ");
            cpfCliente = SCANNER.nextLine().strip().replaceAll(" ", "");
            if (Pattern.matches("^[a-zA-Z!@#$%^&*(),.?\":{}|<>]+$", cpfCliente)) {
                System.out.println("CPF inválido! Insira novamente.");
            } else break;
        }

        Cliente cliente = new Cliente(cpfCliente, nomeCliente);

        try {
            return clienteRepository.adicionar(cliente);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void remover(){

    }

    public void exibirCliente(Conta conta){

    }
}
