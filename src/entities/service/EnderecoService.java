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
        ArrayList<Endereco> enderecos = new ArrayList<>();
        while(!enderecoInput.equalsIgnoreCase("ENCERRAR ENDEREÇOS")){
            System.out.println("Insira [ENCERRAR ENDEREÇOS] para parar de adicionar endereços do cliente");
            System.out.print("Insira o Logradouro do Endereço do cliente: ");
            enderecoInput = SCANNER.nextLine();

            String cidade, estado, pais, cep;

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
            StringBuilder message = new StringBuilder("Selecione um endereço para deletar:\n");
            for(int i=0;i<enderecos.size();i++){
                message.append("[").append(i + 1).append("] Logradouro: ").append(enderecos.get(i).getLogradouro()).append("; Cidade: ").append(enderecos.get(i).getCidade()).append("; Estado: ").append(enderecos.get(i).getEstado()).append("; País: ").append(enderecos.get(i).getPais()).append("; CEP: ").append(enderecos.get(i).getCep()).append("\n");
            }

            inputExclusaoEndereco = askInt(String.valueOf(message));

            if (inputExclusaoEndereco > 0 && inputExclusaoEndereco <= enderecos.size()) {
                try{
                    if(this.enderecoRepository.remover(enderecos.get(inputExclusaoEndereco-1).getIdEndereco())){
                        System.out.println("ENDEREÇO removido com sucesso!");
                    }else{
                        System.out.println("Falha ao deletar ENDEREÇO!");
                    }
                }catch(BancoDeDadosException e){
                    e.printStackTrace();
                }
            }else{
                System.out.println("Nenhum endereço selecionado!");
            }
        }else{
            System.out.println("Você tem apenas [1] endereço e não pode exluí-lo!");
        }
    }

    public void alterarEndereco(Conta conta){
        List<Endereco> enderecos = this.retornarEnderecosDoCliente(conta);
        int inputAlteracaoEndereco, tipoAlteracaoEndereco;

        StringBuilder message = new StringBuilder("Selecione um endereço para alterar:\n");
        for(int i=0;i<enderecos.size();i++){
            message.append("[").append(i + 1).append("] Logradouro: ").append(enderecos.get(i).getLogradouro()).append("; Cidade: ").append(enderecos.get(i).getCidade()).append("; Estado: ").append(enderecos.get(i).getEstado()).append("; País: ").append(enderecos.get(i).getPais()).append("; CEP: ").append(enderecos.get(i).getCep()).append("\n");
        }

        inputAlteracaoEndereco = askInt(String.valueOf(message));

        if(inputAlteracaoEndereco > 0 && inputAlteracaoEndereco <= enderecos.size()){
            Endereco novoEndereco = enderecos.get(inputAlteracaoEndereco);

            System.out.println("Selecione a alteração que quer fazer no Endereço:");
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
                switch(tipoAlteracaoEndereco){
                    case 1 -> novoEndereco.setLogradouro(askString("Insira o novo [Logradouro]: "));
                    case 2 -> novoEndereco.setCidade(askString("Insira a nova [Cidade]: "));
                    case 3 -> novoEndereco.setEstado(askString("Insira o novo [Estado]: "));
                    case 4 -> novoEndereco.setPais(askString("Insira o novo [País]: "));
                    case 5 -> novoEndereco.setCep(askString("Insira o novo [CEP]: "));
                    default -> System.err.println("Erro bizarro!");
                }
                try{
                    if(this.enderecoRepository.editar(novoEndereco.getIdEndereco(), novoEndereco)){
                        System.out.println("ENDEREÇO alterado com sucesso!");
                    }else{
                        System.err.println("Problemas ao editar o ENDEREÇO");
                    }
                }catch(BancoDeDadosException e){
                    e.printStackTrace();
                }
            }
        }else{
            System.out.println("Nenhum endereço selecionado!");
        }
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
