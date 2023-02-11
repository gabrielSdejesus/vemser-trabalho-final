package entities.view;

import entities.controller.BancoDeDados;
import entities.interfaces.Tela;
import entities.model.Cliente;
import entities.model.Conta;
import entities.model.Contato;
import entities.model.Endereco;

import java.util.ArrayList;
import java.util.Scanner;

public class TelaPerfil  implements Tela {
    public static void exibirTelaPerfil(){
        System.out.println("Você está na Tela de Perfil");
        TelaPerfil.tratarInput(TelaPerfil.pedirInput());
    }

    public static void tratarInput(int input) {
        Scanner scanner = new Scanner(System.in);
        Conta login = Tela.login();
        switch(input){
            case 1 ->{
                String senhaCliente, numeroCliente;
                System.out.println("Insira o número da sua conta:");
                numeroCliente = scanner.nextLine();
                System.out.println("Insira a senha do seu cliente:");
                senhaCliente = scanner.nextLine();
                Conta conta = BancoDeDados.consultarNumeroDaConta(numeroCliente);
                if(conta != null && conta.getCliente().verificarSenha(senhaCliente)){
                    conta.getCliente().exibir();
                }else{
                    System.err.println("Número de conta ou senha do cliente");
                    System.out.println("Login mal-sucedido");
                }
                exibirTelaPerfil();
            }
            case 2 ->{
                if(login != null){
                    login.exibir();
                }else{
                    System.out.println("Login mal-sucedido");
                }
                exibirTelaPerfil();
            }
            case 3 ->{
                String senhaAdm;
                System.out.println("Insira a senha Adminsitrativa [ABACAXI]:");
                senhaAdm = scanner.nextLine();

                if(senhaAdm.equals("ABACAXI")){
                    ArrayList<Contato> contatos = new ArrayList<>();
                    String contatoInput = "";
                    while(!contatoInput.equalsIgnoreCase("ENCERRAR CONTATOS")){
                        System.out.println("Insira [ENCERRAR CONTATOS] para parar de adicionar contatos");
                        System.out.println("Insira o telefone do contato:");
                        contatoInput = scanner.nextLine();

                        String email;

                        if(contatoInput.equalsIgnoreCase("ENCERRAR CONTATOS")){
                            if (contatos.size()<1){
                                System.out.println("Você deve adicionar ao menos um Contato!");
                                contatoInput = "";
                            }else{
                                break;
                            }
                        }else{
                            System.out.println("Insira o email do Contato");
                            email = scanner.nextLine();
                            contatos.add(new Contato(contatoInput, email));
                        }
                    }

                    ArrayList<Endereco> enderecos = new ArrayList<>();
                    String enderecoInput = "";
                    while(!enderecoInput.equalsIgnoreCase("ENCERRAR ENDEREÇOS")){
                        System.out.println("Insira [ENCERRAR ENDEREÇOS] para parar de adicionar endereços");
                        System.out.println("Insira o Logradouro do endereço:");
                        enderecoInput = scanner.nextLine();

                        String cidade, estado, pais, cep;

                        if(enderecoInput.equalsIgnoreCase("ENCERRAR CONTATOS")){
                            if (contatos.size()<1){
                                System.out.println("Você deve adicionar ao menos um Endereço!");
                                enderecoInput = "";
                            }else{
                                break;
                            }
                        }else{
                            System.out.println("Insira a Cidade do Endereço");
                            cidade = scanner.nextLine();
                            System.out.println("Insira o Estado do Endereço");
                            estado = scanner.nextLine();
                            System.out.println("Insira o País do Endereço");
                            pais = scanner.nextLine();
                            System.out.println("Insira o CEP do Endereço");
                            cep = scanner.nextLine();
                            enderecos.add(new Endereco(enderecoInput, cidade, estado, pais, cep));
                        }
                    }
                    Cliente cliente = new Cliente("nome", "cpf", enderecos, contatos, "login", "senha");
                    Conta conta = new Conta(cliente, 0);
                    BancoDeDados.adicionarConta(conta);
                }else{
                    System.err.println("Número de conta ou senha inválida");
                }
                exibirTelaPerfil();
            }
            case 4 -> {
                if(login != null){
                    ArrayList<Contato> contatos = login.getCliente().getContatos();
                    int inputAlteracaoContato, tipoAlteracaoContato;
                    String novoDado;

                    System.out.println("Selecione um contato para alterar:");
                    for(int i=0;i<contatos.size();i++){
                        System.out.printf("[%d] Telefone: %s; Email: %s", (i+1), contatos.get(i).getTelefone(), contatos.get(i).getEmail());
                    }

                    inputAlteracaoContato = Integer.parseInt(scanner.nextLine());

                    if(inputAlteracaoContato > 0 && inputAlteracaoContato <= contatos.size()-1){

                        System.out.println("Selecione a alteração que quer fazer no Contato:");
                        System.out.println("[1] Telefone:");
                        System.out.println("[2] Email:");
                        System.out.println("[3] Cancelar");

                        tipoAlteracaoContato = Integer.parseInt(scanner.nextLine());
                        if (tipoAlteracaoContato > 0 && inputAlteracaoContato <= contatos.size()-1){
                            System.out.println("Operação cancelada!");
                        }else{
                            String tipoContato = "";
                            switch(tipoAlteracaoContato){
                                case 1 -> tipoContato = "Telefone";
                                case 2 -> tipoContato = "Email";
                                default -> System.err.println("Erro bizarro!");
                            }
                            System.out.println("Insira o novo ["+tipoContato+"]:");
                            novoDado = scanner.nextLine();
                            contatos.get(inputAlteracaoContato-1).alterarDado(tipoContato, novoDado);
                        }
                    }else{
                        System.out.println("Nenhum contato selecionado!");
                    }

                }else{
                    System.out.println("Login mal-sucedido");
                }
                exibirTelaPerfil();
            }
            case 5 -> {
                if(login != null){
                    ArrayList<Endereco> enderecos = login.getCliente().getEnderecos();
                    int inputAlteracaoEndereco, tipoAlteracaoEndereco;
                    String novoDado;

                    System.out.println("Selecione um contato para alterar:");
                    for(int i=0;i<enderecos.size();i++){
                        System.out.printf("[%d] Logradouro: %s; Cidade: %s; Estado: %s; País: %s; CEP: %s", (i+1), enderecos.get(i).getLogradouro(), enderecos.get(i).getCidade(), enderecos.get(i).getEstado(), enderecos.get(i).getPais(), enderecos.get(i).getCep());
                    }

                    inputAlteracaoEndereco = Integer.parseInt(scanner.nextLine());

                    if(inputAlteracaoEndereco > 0 && inputAlteracaoEndereco <= enderecos.size()-1){

                        System.out.println("Selecione a alteração que quer fazer no Contato:");
                        System.out.println("[1] Logradouro:");
                        System.out.println("[2] Cidade:");
                        System.out.println("[3] Estado:");
                        System.out.println("[4] País:");
                        System.out.println("[5] CEP:");
                        System.out.println("[6] Cancelar");

                        tipoAlteracaoEndereco = Integer.parseInt(scanner.nextLine());
                        if (tipoAlteracaoEndereco > 0 && inputAlteracaoEndereco <= enderecos.size()-1){
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
                            System.out.println("Insira o novo ["+tipoEndereco+"]:");
                            novoDado = scanner.nextLine();
                            enderecos.get(inputAlteracaoEndereco-1).alterarDado(tipoEndereco, novoDado);
                        }
                    }else{
                        System.out.println("Nenhum endereço selecionado!");
                    }

                }else{
                    System.out.println("Login mal-sucedido");
                }
                exibirTelaPerfil();
            }
            case 6 -> {
                if(login != null) {
                    ArrayList<Contato> contatos = login.getCliente().getContatos();
                    int inputExclusaoContato;

                    if(contatos.size()>1){
                        System.out.println("Selecione um contato para deletar:");
                        for (int i = 0; i < contatos.size(); i++) {
                            System.out.printf("[%d] Telefone: %s; Email: %s", (i + 1), contatos.get(i).getTelefone(), contatos.get(i).getEmail());
                        }

                        inputExclusaoContato = Integer.parseInt(scanner.nextLine());

                        if (inputExclusaoContato > 0 && inputExclusaoContato <= contatos.size()-1) {
                            System.out.printf("Contato [%d] excluído!", inputExclusaoContato);
                            login.getCliente().getContatos().remove(inputExclusaoContato-1);
                        }else{
                            System.out.println("Nenhum contato selecionado!");
                        }
                    }else{
                        System.out.println("Você tem apenas [1] contato e não pode excluí-lo!");
                    }
                }
                exibirTelaPerfil();
            }
            case 7 -> {
                if(login != null) {
                    ArrayList<Endereco> enderecos = login.getCliente().getEnderecos();
                    int inputExclusaoEndereco;

                    if(enderecos.size() > 1){
                        System.out.println("Selecione um contato para deletar:");
                        for (int i = 0; i < enderecos.size(); i++) {
                            System.out.printf("[%d] Logradouro: %s; Cidade: %s; Estado: %s; País: %s; CEP: %s", (i+1), enderecos.get(i).getLogradouro(), enderecos.get(i).getCidade(), enderecos.get(i).getEstado(), enderecos.get(i).getPais(), enderecos.get(i).getCep());
                        }

                        inputExclusaoEndereco = Integer.parseInt(scanner.nextLine());

                        if (inputExclusaoEndereco > 0 && inputExclusaoEndereco <= enderecos.size()-1) {
                            System.out.printf("Contato [%d] excluído!", inputExclusaoEndereco);
                            login.getCliente().getEnderecos().remove(inputExclusaoEndereco-1);
                        }else{
                            System.out.println("Nenhum endereço selecionado!");
                        }
                    }else{
                        System.out.println("Você tem apenas [1] endereço e não pode exluí-lo!");
                    }
                }
                exibirTelaPerfil();
            }
            case 8 -> Tela.redirecionarParaTela(1);
            default -> {
                System.out.println("Opção inválida!");
                exibirTelaPerfil();
            }
        }
    }

    public static int pedirInput() {
        System.out.println("[1] -> Insira seus dados de login para exibir seus dados de cliente\n[2] -> Insira seus dados de login para exibir os dados da sua conta\n[3] -> Cadastrar um novo Cliente com Conta\n[4] -> Alterar Contato do Cliente\n[5] -> Alterar Endereço do Cliente\n[6] -> Deletar Contato do Cliente\n[7] -> Deletar Endereço do Cliente\n[8] -> Voltar para a Tela Principal");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }
}
