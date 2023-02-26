package entities.service;

import entities.exception.BancoDeDadosException;
import entities.model.Cliente;
import entities.model.Conta;
import entities.model.Endereco;
import entities.repository.EnderecoRepository;
import java.util.ArrayList;
import java.util.List;

public class EnderecoService extends Service{

    private EnderecoRepository enderecoRepository;

    public EnderecoService() {
        this.enderecoRepository = new EnderecoRepository();
    }

    public void adicionarEndereco(Conta conta){
        String enderecoInput = "";
        List<Endereco> enderecos = new ArrayList<>();
        while(!enderecoInput.equalsIgnoreCase("SAIR")){
            System.out.println("\nInsira SAIR para parar de adicionar endereços do cliente");
            do {
                enderecoInput = askString("Insira o Logradouro do Endereço do cliente: ");
                if(enderecoInput.equals("")){
                    System.err.println("Valor inválido! Tente novamente!");
                }
            } while (enderecoInput.equals(""));

            String cidade, estado, pais, cep;

            if(enderecoInput.equalsIgnoreCase("SAIR")){
                if (enderecos.size()<1){
                    System.err.println("Você deve adicionar ao menos um Endereço!");
                    enderecoInput = "";
                }else{
                    System.out.println();
                    break;
                }
            }else{

                do {
                    cidade = askString("Insira a Cidade do Endereço do cliente: ");
                } while (cidade.equals(""));

                do {
                    estado = askString("Insira o Estado do Endereço do cliente: ");
                } while (estado.equals(""));

                do {
                    pais = askString("Insira o País do Endereço do cliente: ");
                } while (pais.equals(""));

                do {
                    cep = askString("Insira o CEP do Endereço do cliente: ");
                    if(cep.length() != 8){
                        System.err.println("CEP inválido!");
                    }
                } while (cep.length() != 8);

                Cliente cliente = new Cliente();
                cliente.setIdCliente(conta.getCliente().getIdCliente());

                Endereco endereco = new Endereco();
                endereco.setCliente(cliente);
                endereco.setCep(cep);
                endereco.setCidade(cidade);
                endereco.setEstado(estado);
                endereco.setLogradouro(enderecoInput);
                endereco.setPais(pais);

                enderecos.add(endereco);
                System.err.println("Endereço adicionado!");
            }
        }
        if(enderecos.size() > 0){
            for(Endereco endereco: enderecos){
                try{
                    this.enderecoRepository.adicionar(endereco);
                }catch(BancoDeDadosException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void deletarEndereco(Conta conta){
        List<Endereco> enderecos = this.retornarEnderecosDoCliente(conta);
        int inputExclusaoEndereco;

        if(enderecos.size() > 1){
            StringBuilder message = new StringBuilder("\nSelecione um endereço para deletar:\n");
            for(int i=0;i<enderecos.size();i++){
                message.append("[").append(i + 1).append("] Logradouro: ").append(enderecos.get(i).getLogradouro()).append("; Cidade: ").append(enderecos.get(i).getCidade()).append("; Estado: ").append(enderecos.get(i).getEstado()).append("; País: ").append(enderecos.get(i).getPais()).append("; CEP: ").append(enderecos.get(i).getCep());
                if(i != (enderecos.size() - 1)){
                    message.append("\n");
                }
            }


            inputExclusaoEndereco = askInt(String.valueOf(message));

            if (inputExclusaoEndereco > 0 && inputExclusaoEndereco <= enderecos.size()) {
                try{
                    if(this.enderecoRepository.remover(enderecos.get(inputExclusaoEndereco-1).getIdEndereco())){
                        System.err.println("\nEndereço removido com sucesso!");
                    }else{
                        System.err.println("\nFalha ao deletar endereço!");
                    }
                }catch(BancoDeDadosException e){
                    e.printStackTrace();
                }
            }else{
                System.err.println("\nNenhum endereço selecionado!");
            }
        }else{
            System.err.println("\nVocê tem apenas [1] endereço e não pode exluí-lo!");
        }
    }

    public void alterarEndereco(Conta conta){
        List<Endereco> enderecos = this.retornarEnderecosDoCliente(conta);
        int inputAlteracaoEndereco, tipoAlteracaoEndereco = 0;

        StringBuilder message = new StringBuilder("\nSelecione um endereço para alterar:\n");
        for(int i=0;i<enderecos.size();i++){
            message.append("[").append(i + 1).append("] Logradouro: ").append(enderecos.get(i).getLogradouro()).append("; Cidade: ").append(enderecos.get(i).getCidade()).append("; Estado: ").append(enderecos.get(i).getEstado()).append("; País: ").append(enderecos.get(i).getPais()).append("; CEP: ").append(enderecos.get(i).getCep());
            if(i != (enderecos.size() - 1)){
                message.append("\n");
            }
        }

        inputAlteracaoEndereco = askInt(String.valueOf(message));
        if(inputAlteracaoEndereco > enderecos.size()){
            System.err.println("Opção inválida!\n");
            return;
        }

        do{
            if(inputAlteracaoEndereco > 0){
                Endereco novoEndereco = enderecos.get(inputAlteracaoEndereco-1);

                System.out.println("\nSelecione a alteração que quer fazer no Endereço:");
                System.out.println("[1] Logradouro");
                System.out.println("[2] Cidade");
                System.out.println("[3] Estado");
                System.out.println("[4] País");
                System.out.println("[5] CEP");
                System.out.println("[0] Cancelar - Voltar para tela de perfil");

                System.out.print("Insira aqui: ");
                tipoAlteracaoEndereco = Integer.parseInt(SCANNER.nextLine());

                if(tipoAlteracaoEndereco == 0){
                    System.out.println();
                    return;
                }

              if (tipoAlteracaoEndereco > 0 && tipoAlteracaoEndereco < 6){
                    boolean adicionar = true;
                    switch(tipoAlteracaoEndereco){
                        case 1 -> {
                            novoEndereco.setLogradouro(askString("Insira o novo [Logradouro]: "));
                            if(novoEndereco.getLogradouro().equals("")){
                                adicionar = false;
                            }
                        }
                        case 2 -> {
                            novoEndereco.setCidade(askString("Insira a nova [Cidade]: "));
                            if(novoEndereco.getCidade().equals("")){
                                adicionar = false;
                            }
                        }
                        case 3 -> {
                            String novoEstado = askString("Insira o novo [Estado]: ");
                            if (novoEstado.equals("")) {
                                adicionar = false;
                            } else {
                                novoEndereco.setEstado(novoEstado);
                            }
                            if(novoEndereco.getEstado().equals("")){
                                adicionar = false;
                            }
                        }
                        case 4 -> {
                            String novoPais = askString("Insira o novo [País]: ").toUpperCase();
                            if (!novoPais.matches("[a-zA-Z]+") || novoEndereco.getPais().equals("")) {
                                adicionar = false;
                            } else {
                                novoEndereco.setPais(novoPais);
                            }
                            if(novoEndereco.getPais().equals("")){
                                adicionar = false;
                            }
                        }
                        case 5 -> {
                            String novoCep = askString("Insira o novo [CEP]: ");
                            if (!novoCep.matches("[0-9]+") || novoCep.length() != 8) {
                                adicionar = false;
                            } else {
                                novoEndereco.setCep(novoCep);
                            }
                        }
                        default -> System.err.println("Erro bizarro!");
                    }
                    if(adicionar){
                        try{
                            if(this.enderecoRepository.editar(conta.getCliente().getIdCliente(), novoEndereco)){
                                System.err.println("\nEndereço alterado com sucesso!");
                            }else{
                                System.err.println("\nProblemas ao editar o ENDEREÇO");
                            }
                        }catch(BancoDeDadosException e){
                            e.printStackTrace();
                        }
                    }else{
                        System.err.println("Valor inserido inválido! Tente novamente");
                    }
                }
                if(tipoAlteracaoEndereco > 5){
                    System.err.println("Opção inválida!");
                }
            }
        }while(tipoAlteracaoEndereco > 5);
    } 

    public List<Endereco> retornarEnderecosDoCliente(Conta conta){
        List<Endereco> enderecos;
        try{
            enderecos = this.enderecoRepository.listarEnderecosPorPessoa(conta.getCliente().getIdCliente());
            return enderecos;
        }catch (BancoDeDadosException e){
            e.printStackTrace();
        }
        return null;
    }
}
