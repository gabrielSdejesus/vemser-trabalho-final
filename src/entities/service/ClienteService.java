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
}
