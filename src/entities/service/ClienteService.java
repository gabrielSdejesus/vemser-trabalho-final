package entities.service;

import entities.exception.BancoDeDadosException;
import entities.model.Cliente;
import entities.repository.ClienteRepository;

import java.util.List;
import java.util.regex.Pattern;

public class ClienteService extends Service{
    private ClienteRepository clienteRepository;

    public ClienteService() {
        this.clienteRepository = new ClienteRepository();
    }

    public Cliente adicionarCliente(){
        String nomeCliente, cpfCliente;

        while (true) {
            System.out.print("\nInsira o nome do cliente: ");
            nomeCliente = SCANNER.nextLine().strip().toUpperCase();
            if (Pattern.matches("[0-9!@#$%^&*(),.?\":{}|<>]", nomeCliente)) {
                System.out.println("Nome inválido! Insira novamente.");
            } else break;
        }

        while (true) {
            System.out.print("Insira o CPF do cliente: ");
            cpfCliente = SCANNER.nextLine().strip().replaceAll(" ", "");
            if (Pattern.matches("^[a-zA-Z!@#$%^&*(),.?\":{}|<>]+$", cpfCliente) || cpfCliente.isEmpty()) {
                System.out.println("CPF inválido! Insira novamente.");
            } else break;
        }

        Cliente cliente = new Cliente();
        cliente.setCpf(cpfCliente);
        cliente.setNome(nomeCliente);

        try {
            return clienteRepository.adicionar(cliente);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void deletarCliente(int idCliente){
        try{
            if(this.clienteRepository.remover(idCliente)){
                System.err.println("Cliente removido com sucesso!");
            }else{
                System.err.println("ERR: Problemas ao remover o cliente!");
            }
        }catch (BancoDeDadosException e){
            e.printStackTrace();
        }
    }

    public void alterarCliente(){
        try{
            List<Cliente> clientes = this.clienteRepository.listar();

            int inputClienteEscolhido;

            StringBuilder message = new StringBuilder("Selecione um CLIENTE para editar:\n");
            for(int i=0;i<clientes.size();i++){
                message.append("[").append(i + 1).append("] Nome: ").append(clientes.get(i).getNome()).append("; Cpf: ").append(clientes.get(i).getCpf()).append("\n");
            }
            inputClienteEscolhido = askInt(String.valueOf(message));

            if(inputClienteEscolhido > 0 && inputClienteEscolhido <= clientes.size()){
                Cliente novoCliente = clientes.get(inputClienteEscolhido-1);

                inputClienteEscolhido = askInt("Selecione a alteração que quer fazer no Contato:\n[1] Nome\n[2] CPF\n[3] Cancelar");
                if (inputClienteEscolhido < 0 || inputClienteEscolhido >= 3){
                    System.out.println("Operação cancelada!");
                }else{
                    boolean editar = true;
                    switch(inputClienteEscolhido){
                        case 1 -> {
                            novoCliente.setNome(askString("Insira o novo [Nome]: ").toUpperCase());
                            if(novoCliente.getNome().equals("")){
                                editar = false;
                            }
                        }
                        case 2 -> {
                            novoCliente.setCpf(askString("Insira o novo [CPF]: "));
                            if(novoCliente.getCpf().equals("") || novoCliente.getCpf().length() != 12){
                                editar = false;
                            }
                        }
                        default -> System.err.println("Erro bizarro!");
                    }
                    if(editar){
                        try{
                            if(this.clienteRepository.editar(novoCliente.getIdCliente(), novoCliente)){
                                System.out.println("CLIENTE alterado com sucesso!");
                            }else{
                                System.err.println("Problemas ao editar o CLIENTE");
                            }
                        }catch(BancoDeDadosException e){
                            e.printStackTrace();
                        }
                    }else{
                        System.err.println("Valor inserido inválido!");
                    }
                }
            }else{
                System.err.println("Esse número não representa um CLIENTE!");
            }
        }catch(BancoDeDadosException e){
            e.printStackTrace();
        }
    }

    public void exibirCliente(int idCliente){
        try{
            Cliente cliente = this.clienteRepository.consultarPorIdCliente(idCliente);
            if(cliente != null){
                System.out.println("\n\tDados Pessoais\n" + "\tNome: "+ cliente.getNome());
                System.out.println("\tCPF: "+ cliente.getCpf() + "\n");
            }else{
                System.err.println("Dados de cliente não encontrado!\n");
            }
        }catch (BancoDeDadosException e){
            e.printStackTrace();
        }
    }

    public void listarClientes() {
        try {
            System.out.println();
            List<Cliente> clientes = clienteRepository.listar();
            clientes.forEach(System.out::println);
            System.out.println();
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }
}
