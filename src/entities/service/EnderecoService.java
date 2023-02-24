package entities.service;

import entities.model.Cliente;
import entities.model.Conta;
import entities.model.Endereco;
import entities.repository.EnderecoRepository;

import java.util.ArrayList;

public class EnderecoService extends Service{

    private EnderecoRepository enderecoRepository;

    public EnderecoService() {
        this.enderecoRepository = new EnderecoRepository();
    }

    public void adicionarEndereco(Conta conta){
        String enderecoInput = "";
        ArrayList<Endereco> enderecos = new ArrayList<>();
        while(!enderecoInput.equalsIgnoreCase("ENCERRAR ENDEREÇOS")){
            System.out.println("Insira [ENCERRAR ENDEREÇOS] para parar de adicionar endereços do cliente");
            System.out.print("Insira o Logradouro do Endereço do cliente: ");
            enderecoInput = SCANNER.nextLine();

            String cidade, estado, pais, cep, logradouro;

            if(enderecoInput.equalsIgnoreCase("ENCERRAR ENDEREÇOS")){
                if (enderecos.size()<1){
                    System.out.println("Você deve adicionar ao menos um Endereço!");
                    enderecoInput = "";
                }else{
                    break;
                }
            }else{
                System.out.print("Insira a Cidade do Endereço do cliente: ");
                cidade = SCANNER.nextLine();
                System.out.print("Insira o Estado do Endereço do cliente: ");
                estado = SCANNER.nextLine();
                System.out.print("Insira o País do Endereço do cliente: ");
                pais = SCANNER.nextLine();
                System.out.print("Insira o CEP do Endereço do cliente: ");
                cep = SCANNER.nextLine();

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
                System.out.println("\tEndereço adicionado!");
            }
        }
        EnderecoService enderecoService = new EnderecoService();
        for(Endereco endereco: enderecos){
            enderecoService.adicionarEndereco(endereco);
        }
    }

    public void deletarEndereco(Conta conta){
        ArrayList<Endereco> enderecos = conta.getCliente().getEnderecos();
        int inputExclusaoEndereco;

        if(enderecos.size() > 1){
            System.out.println("Selecione um endereço para deletar:");
            for (int i = 0; i < enderecos.size(); i++) {
                System.out.printf("[%d] Logradouro: %s; Cidade: %s; Estado: %s; País: %s; CEP: %s", (i+1), enderecos.get(i).getLogradouro(), enderecos.get(i).getCidade(), enderecos.get(i).getEstado(), enderecos.get(i).getPais(), enderecos.get(i).getCep());
            }

            inputExclusaoEndereco = Integer.parseInt(SCANNER.nextLine());

            if (inputExclusaoEndereco > 0 && inputExclusaoEndereco <= enderecos.size()) {
                System.out.printf("Contato [%d] excluído!", inputExclusaoEndereco);
                conta.getCliente().removerEndereco(inputExclusaoEndereco-1);
            }else{
                System.out.println("Nenhum endereço selecionado!");
            }
        }else{
            System.out.println("Você tem apenas [1] endereço e não pode exluí-lo!");
        }
    }

    public void alterarEndereco(Conta conta){
        ArrayList<Endereco> enderecos = conta.getCliente().getEnderecos();
        int inputAlteracaoEndereco, tipoAlteracaoEndereco;
        String novoDado;

        System.out.println("Selecione um endereço para alterar:");
        for(int i=0;i<enderecos.size();i++){
            System.out.printf("[%d] Logradouro: %s; Cidade: %s; Estado: %s; País: %s; CEP: %s\n", (i+1), enderecos.get(i).getLogradouro(), enderecos.get(i).getCidade(), enderecos.get(i).getEstado(), enderecos.get(i).getPais(), enderecos.get(i).getCep());
        }

        inputAlteracaoEndereco = Integer.parseInt(SCANNER.nextLine());

        if(inputAlteracaoEndereco > 0 && inputAlteracaoEndereco <= enderecos.size()){

            System.out.println("Selecione a alteração que quer fazer no Contato:");
            System.out.println("[1] Logradouro");
            System.out.println("[2] Cidade");
            System.out.println("[3] Estado");
            System.out.println("[4] País");
            System.out.println("[5] CEP");
            System.out.println("[6] Cancelar - Voltar para tela de perfil");

            tipoAlteracaoEndereco = Integer.parseInt(SCANNER.nextLine());
            if (tipoAlteracaoEndereco < 1 || tipoAlteracaoEndereco >= 6){
                System.out.println("Operação cancelada!");
            }else{
                String tipoEndereco = "";
                switch(tipoAlteracaoEndereco){
                    case 1 -> tipoEndereco = "Logradouro";
                    case 2 -> tipoEndereco = "Cidade";
                    case 3 -> tipoEndereco = "Estado";
                    case 4 -> tipoEndereco = "País";
                    case 5 -> tipoEndereco = "CEP";
                    default -> System.err.println("Erro bizarro!");
                }
                System.out.print("Insira o novo ["+tipoEndereco+"]: ");
                novoDado = SCANNER.nextLine();
                enderecos.get(inputAlteracaoEndereco-1).alterarDado(tipoEndereco.toLowerCase(), novoDado);
                System.out.println("Endereço alterado com sucesso!");
            }
        }else{
            System.out.println("Nenhum endereço selecionado!");
        }
    }
}
