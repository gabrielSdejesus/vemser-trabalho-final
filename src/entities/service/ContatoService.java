package entities.service;

import entities.exception.BancoDeDadosException;
import entities.model.Cliente;
import entities.model.Conta;
import entities.model.Contato;
import entities.model.Endereco;
import entities.repository.ContatoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ContatoService extends Service{

    private ContatoRepository contatoRepository;

    public ContatoService() {
        this.contatoRepository = new ContatoRepository();
    }

    public void alterarContato(Conta conta){
        List<Contato> contatos = this.retornarContatosDoCliente(conta);
        int inputAlteracaoContato, tipoAlteracaoContato;

        StringBuilder message = new StringBuilder("Selecione um contato para alterar:\n");
        for(int i=0;i<contatos.size();i++){
            message.append("[").append(i + 1).append("] Telefone: ").append(contatos.get(i).getTelefone()).append("; Email: ").append(contatos.get(i).getEmail()).append("\n");
        }
        inputAlteracaoContato = askInt(String.valueOf(message));

        if(inputAlteracaoContato > 0 && inputAlteracaoContato <= contatos.size()){
            Contato novoContato = contatos.get(inputAlteracaoContato - 1);

            System.out.println("Selecione a alteração que quer fazer no Contato:");
            System.out.println("[1] Telefone");
            System.out.println("[2] Email");
            System.out.println("[3] Cancelar - Voltar para tela de perfil");

            tipoAlteracaoContato = Integer.parseInt(SCANNER.nextLine());
            if (tipoAlteracaoContato < 1 || tipoAlteracaoContato >= 3){
                System.out.println("Operação cancelada!");
            }else{
                boolean editar = true;
                switch(tipoAlteracaoContato){
                    case 1 -> {
                        String novoTelefone;
                        while (true) {
                            novoTelefone = askString("Insira o novo [Telefone] (apenas números): ");
                            if (Pattern.matches("[a-zA-Z!@#$%^&*(),.?\":{}|<>]", novoTelefone)) {
                                System.err.println("Número inválido! Tente novamente.");
                            } else {
                                novoContato.setTelefone(novoTelefone);
                                break;
                            }
                        }
                        if(novoContato.getTelefone().equals("")){
                            editar = false;
                        }
                    }
                    case 2 -> {
                        String novoEmail;
                        while (true) {
                            novoEmail = askString("Insira o novo [Email]: ");
                            if (!Pattern.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", novoEmail)) {
                                System.err.println("Email inválido! Tente novamente.");
                            } else {
                                novoContato.setEmail(novoEmail);
                                break;
                            }
                        }

                        if(novoContato.getEmail().equals("")){
                            editar = false;
                        }
                    }
                    default -> System.err.println("Erro bizarro!");
                }
                if(editar){
                    try{
                        if(this.contatoRepository.editar(novoContato.getIdContato(), novoContato)){
                            System.out.println("CONTATO alterado com sucesso!");
                        }else{
                            System.err.println("Problemas ao editar o CONTATO");
                        }
                    }catch(BancoDeDadosException e){
                        e.printStackTrace();
                    }
                }
            }
        }else{
            System.out.println("Nenhum contato selecionado!");
        }
    }

    public void deletarContato(Conta conta){
        List<Contato> contatos = this.retornarContatosDoCliente(conta);
        int inputExclusaoContato;

        if(contatos.size()>1){
            StringBuilder message = new StringBuilder("Selecione um contato para deletar:\n");

            System.out.println("Selecione um contato para deletar:");
            for (int i = 0; i < contatos.size(); i++) {
                message.append("[").append(i + 1).append("] Telefone: ").append(contatos.get(i).getTelefone()).append("; Email: ").append(contatos.get(i).getEmail());
            }

            inputExclusaoContato = askInt(String.valueOf(message));

            if (inputExclusaoContato > 0 && inputExclusaoContato <= contatos.size()) {
                try{
                    if(this.contatoRepository.remover(contatos.get(inputExclusaoContato-1).getIdContato())){
                        System.out.println("CONTATO removido com sucesso!");
                    }else{
                        System.out.println("Falha ao deletar CONTATO!");
                    }
                }catch(BancoDeDadosException e){
                    e.printStackTrace();
                }
            }else{
                System.out.println("Nenhum contato selecionado!");
            }
        }else{
            System.out.println("Você tem apenas [1] contato e não pode excluí-lo!");
        }
    }

    public void adicionarContato(Conta conta){
        String contatoInput = "";
        ArrayList<Contato> contatos = new ArrayList<>();
        while(!contatoInput.equalsIgnoreCase("ENCERRAR CONTATOS")){
            System.out.println("Insira [ENCERRAR CONTATOS] para parar de adicionar contatos do cliente");
            System.out.print("Insira o telefone do contato do cliente: ");
            contatoInput = SCANNER.nextLine();

            String email;

            if(contatoInput.equalsIgnoreCase("ENCERRAR CONTATOS")){
                if (contatos.size()<1){
                    System.out.println("Você deve adicionar ao menos um Contato!");
                    contatoInput = "";
                }else{
                    break;
                }
            }else{
                System.out.print("Insira o email do Contato do cliente: ");
                email = SCANNER.nextLine();

                Cliente cliente = new Cliente();
                cliente.setIdCliente(conta.getCliente().getIdCliente());

                Contato contato = new Contato();
                contato.setCliente(cliente);
                contato.setEmail(email);
                contato.setTelefone(contatoInput);

                contatos.add(contato);
                System.out.println("\tContato adicionado!");
            }
        }
        if(contatos.size() > 0){
            for(Contato contato: contatos){
                try{
                    this.contatoRepository.adicionar(contato);
                }catch(BancoDeDadosException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Contato> retornarContatosDoCliente(Conta conta){
        List<Contato> contatos;
        try{
            contatos = this.contatoRepository.listarContatosPorPessoa(conta.getCliente().getIdCliente());
            return contatos;
        }catch (BancoDeDadosException e){
            e.printStackTrace();
        }
        return null;
    }
}
