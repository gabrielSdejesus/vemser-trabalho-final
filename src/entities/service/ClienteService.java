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

    public void deletarCliente(){
        try{
            List<Cliente> clientes = this.clienteRepository.listar();

            int inputClienteEscolhido;

            StringBuilder message = new StringBuilder("Selecione um CLIENTE para deletar:\n");
            for(int i=0;i<clientes.size();i++){
                message.append("[").append(i + 1).append("] Nome: ").append(clientes.get(i).getNome()).append("; Cpf: ").append(clientes.get(i).getCpf()).append("\n");
            }
            inputClienteEscolhido = askInt(String.valueOf(message));

            if(inputClienteEscolhido > 0 && inputClienteEscolhido <= clientes.size()){
                if(this.clienteRepository.remover(clientes.get(inputClienteEscolhido).getIdCliente())){
                    System.out.println("CLIENTE removido com sucesso!");
                }else{
                    System.err.println("Problemas ao remover o CLIENTE!");
                }
            }else{
                System.err.println("Esse número não representa um CLIENTE!");
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
                Cliente novoCliente = clientes.get(inputClienteEscolhido);

                inputClienteEscolhido = askInt("Selecione a alteração que quer fazer no Contato:\n[1] Nome\n[2] CPF\n[3] Cancelar");
                if (inputClienteEscolhido < 1 || inputClienteEscolhido >= 3){
                    System.out.println("Operação cancelada!");
                }else{
                    switch(inputClienteEscolhido){
                        case 1 -> novoCliente.setNome(askString("Insira o novo [Nome]: "));
                        case 2 -> novoCliente.setCpf(askString("Insira o novo [CPF]: "));
                        default -> System.err.println("Erro bizarro!");
                    }
                    try{
                        if(this.clienteRepository.editar(novoCliente.getIdCliente(), novoCliente)){
                            System.out.println("CLIENTE alterado com sucesso!");
                        }else{
                            System.err.println("Problemas ao editar o CLIENTE");
                        }
                    }catch(BancoDeDadosException e){
                        e.printStackTrace();
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
                System.out.println("Nome do CLIENTE: "+cliente.getNome());
                System.out.println("CPF do CLIENTE: "+cliente.getCpf());
            }else{
                System.err.println("Problemas ao exibir CLIENTE!");
            }
        }catch (BancoDeDadosException e){
            e.printStackTrace();
        }
    }
}
