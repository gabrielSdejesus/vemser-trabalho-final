package entities.service;

import entities.model.Cliente;
import entities.model.Conta;
import entities.model.Contato;
import entities.model.Endereco;
import entities.view.TelaAdministrador;

import java.util.ArrayList;

public class ClienteService extends Service{
    private ClienteRepository clienteRepository;

    public ClienteService() {
        this.clienteRepository = new ClienteRepository();
    }

    public void cadastrarCliente(){
        String nomeCliente, cpfCliente, senhaCliente;

        System.out.println("\tEtapa [1] de [5]");
        System.out.println("Insira o Nome do cliente:");
        nomeCliente = SCANNER.nextLine();

        System.out.println("\tEtapa [2] de [5]");
        System.out.println("Insira o CPF do cliente:");
        cpfCliente = SCANNER.nextLine();

        if(BancoDeDados.consultarExistenciaPorCPF(cpfCliente) != null) {
            if (BancoDeDados.consultarExistenciaPorCPF(cpfCliente).getCliente().getCpf().equalsIgnoreCase(cpfCliente)) {
                System.err.println("CPF já cadastrado!");
                TelaAdministrador.exibirTelaAdministrador();
            }
        }
        System.out.println("\tEtapa [3] de [5]");
        System.out.println("Insira a Senha do cliente:");
        senhaCliente = SCANNER.nextLine();

        ArrayList<Contato> contatos = new ArrayList<>();
        String contatoInput = "";

        System.out.println("\tEtapa [4] de [5]");
        while(!contatoInput.equalsIgnoreCase("SAIR")){
            System.out.println("Insira [SAIR] para parar de adicionar contatos do cliente");
            System.out.println("Insira o telefone do contato do cliente:");
            contatoInput = SCANNER.nextLine();

            String email;

            if(contatoInput.equalsIgnoreCase("SAIR")){
                if (contatos.size()<1){
                    System.out.println("Você deve adicionar ao menos um Contato!");
                    contatoInput = "";
                }else{
                    break;
                }
            }else{
                System.out.println("Insira o email do Contato do cliente");
                email = SCANNER.nextLine();
                contatos.add(new Contato(contatoInput, email));
                System.out.println("\tContato adicionado!\n");
            }
        }

        ArrayList<Endereco> enderecos = new ArrayList<>();
        String enderecoInput = "";

        System.out.println("\tEtapa [5] de [5]");
        while(!enderecoInput.equalsIgnoreCase("SAIR")){
            System.out.println("Insira [SAIR] para parar de adicionar endereços do cliente");
            System.out.println("Insira o Logradouro do Endereço do cliente:");
            enderecoInput = SCANNER.nextLine();

            String cidade, estado, pais, cep;

            if(enderecoInput.equalsIgnoreCase("SAIR")){
                if (enderecos.size()<1){
                    System.out.println("Você deve adicionar ao menos um Endereço!");
                    enderecoInput = "";
                }else{
                    break;
                }
            }else{
                System.out.println("Insira a Cidade do Endereço do cliente:");
                cidade = SCANNER.nextLine();
                System.out.println("Insira o Estado do Endereço do cliente:");
                estado = SCANNER.nextLine();
                System.out.println("Insira o País do Endereço do cliente:");
                pais = SCANNER.nextLine();
                System.out.println("Insira o CEP do Endereço do cliente:");
                cep = SCANNER.nextLine();
                enderecos.add(new Endereco(enderecoInput, cidade, estado, pais, cep));
                System.out.println("\tEndereço adicionado!\n");
            }
        }
        Cliente cliente = new Cliente(nomeCliente, cpfCliente, enderecos, contatos, senhaCliente);
        Conta conta = new Conta(cliente, 0, senhaCliente);
        System.out.println("\nCliente adicionado com sucesso!");
        System.out.println("\tNÃO ESQUECA DE GUARDAR OS DADOS DE ACESSO DA CONTA E DO CLIENTE!");
        System.out.println("\t\tNúmero da conta do cliente: "+conta.getNumero());
        System.out.println("\t\tSenha do cliente: "+senhaCliente + "\n");
    }

    public void deletarCliente(){
        String cpfCliente;
        if(BancoDeDados.getContas().size() > 0){
            System.err.println("Atenção! Deletar o CLIENTE também deletará sua CONTA");
            System.out.println("Clientes cadastrados");
            for(Conta conta : BancoDeDados.getContas()) {
                System.out.printf("\tCliente: %s | CPF: %s | Nº da conta %d\n",
                        conta.getCliente().getNome(), conta.getCliente().getCpf(), conta.getNumero());
            }
            System.out.println("Insira o CPF do CLIENTE que quer deletar:");
            cpfCliente = SCANNER.nextLine();
            Conta conta = BancoDeDados.consultarExistenciaPorCPF(cpfCliente);
            if(conta != null && conta.getSaldo() > 0){
                String confirmacao;
                System.out.println("Esse CLIENTE ainda tem saldo em sua conta, tem certeza dessa operação? [Y/N]");
                confirmacao = SCANNER.nextLine();
                if(confirmacao.equalsIgnoreCase("Y")){
                    System.out.println("\tContinuando operação!");
                }else{
                    System.err.println("\tOperação encerrada!");
                }
            }

            if(BancoDeDados.deletarCliente(cpfCliente)){
                System.out.println("CLIENTE e CONTA do CLIENTE deletados com sucesso!");
            }else{
                System.err.println("CPF informado não encontrado no Banco De Dados!");
                System.err.println("Operação não realizada!");
            }
        }else{
            System.err.println("Não há CLIENTES cadastradas!");
        }
    }

    public void exibirCliente(Conta conta){
        String senhaConta;
        System.out.println("Insira a senha da sua conta:");
        senhaConta = SCANNER.nextLine();
        if(conta.verificarSenha(senhaConta)){
            conta.getCliente().exibir();
        }
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

    public void alterarEnderecoCliente(Conta conta){
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

    public void deletarEnderecoCliente(Conta conta){
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
                contatos.add(new Contato(contatoInput, email));
                System.out.println("\tContato adicionado!");
            }
        }
        conta.getCliente().addContatos(contatos);
    }

    public void adicionarEnderecoCliente(Conta conta){
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
                endereco.setCliente();
                endereco.setCep();
                endereco.setCidade();
                endereco.setEstado();
                endereco.setEstado();
                endereco.setLogradouro();
                endereco.setPais();

                enderecos.add();
                System.out.println("\tEndereço adicionado!");
            }
        }
        conta.getCliente().addEnderecos(enderecos);
    }
}
