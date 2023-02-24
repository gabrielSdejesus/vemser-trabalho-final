package entities.service;

import entities.model.Cliente;
import entities.model.Conta;
import entities.model.Contato;
import entities.repository.ContatoRepository;

import java.util.ArrayList;

public class ContatoService extends Service{

    private ContatoRepository contatoRepository;

    public ContatoService() {
        this.contatoRepository = new ContatoRepository();
    }

    public void alterarContatoCliente(Conta conta){
        ArrayList<Contato> contatos = conta.getCliente().getContatos();
        int inputAlteracaoContato, tipoAlteracaoContato;
        String novoDado;

        System.out.println("Selecione um contato para alterar: ");
        for(int i=0;i<contatos.size();i++){
            System.out.printf("[%d] Telefone: %s; Email: %s\n", (i+1), contatos.get(i).getTelefone(), contatos.get(i).getEmail());
        }
        inputAlteracaoContato = Integer.parseInt(SCANNER.nextLine());

        if(inputAlteracaoContato > 0 && inputAlteracaoContato <= contatos.size()){

            System.out.println("Selecione a alteração que quer fazer no Contato:");
            System.out.println("[1] Telefone");
            System.out.println("[2] Email");
            System.out.println("[3] Cancelar - Voltar para tela de perfil");

            tipoAlteracaoContato = Integer.parseInt(SCANNER.nextLine());
            if (tipoAlteracaoContato < 1 || tipoAlteracaoContato >= 3){
                System.out.println("Operação cancelada!");
            }else{
                String tipoContato = "";
                switch(tipoAlteracaoContato){
                    case 1 -> tipoContato = "Telefone";
                    case 2 -> tipoContato = "Email";
                    default -> System.err.println("Erro bizarro!");
                }
                System.out.print("Insira o novo ["+tipoContato+"]: ");
                novoDado = SCANNER.nextLine();
                contatos.get(inputAlteracaoContato-1).alterarDado(tipoContato.toLowerCase(), novoDado);
                System.out.println("Contato alterado com sucesso!");
            }
        }else{
            System.out.println("Nenhum contato selecionado!");
        }
    }

    public void deletarContatoCliente(Conta conta){
        ArrayList<Contato> contatos = conta.getCliente().getContatos();
        int inputExclusaoContato;

        if(contatos.size()>1){
            System.out.println("Selecione um contato para deletar:");
            for (int i = 0; i < contatos.size(); i++) {
                System.out.printf("[%d] Telefone: %s; Email: %s", (i + 1), contatos.get(i).getTelefone(), contatos.get(i).getEmail());
            }

            inputExclusaoContato = Integer.parseInt(SCANNER.nextLine());

            if (inputExclusaoContato > 0 && inputExclusaoContato <= contatos.size()) {
                System.out.printf("Contato [%d] excluído!", inputExclusaoContato);
                conta.getCliente().removerContato(inputExclusaoContato-1);
            }else{
                System.out.println("Nenhum contato selecionado!");
            }
        }else{
            System.out.println("Você tem apenas [1] contato e não pode excluí-lo!");
        }
    }

    public void adicionarContatoCliente(Conta conta){
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
        ContatoService contatoService = new ContatoService();
        for(Contato contato: contatos){
            contatoService.adicionarContato(contato);
        }
    }
}
