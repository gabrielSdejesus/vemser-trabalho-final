package entities.view;

import entities.controller.BancoDeDados;
import entities.interfaces.Tela;
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
        Conta login;
        switch(input){
            case 1 ->{
                String senhaCliente, numeroConta;
                System.out.println("Insira o número da sua Conta:");
                numeroConta = scanner.nextLine();
                System.out.println("Insira a sua senha de Cliente:");
                senhaCliente = scanner.nextLine();
                Conta conta = BancoDeDados.consultarNumeroDaConta(numeroConta);
                if(conta != null && conta.getCliente().verificarSenha(senhaCliente)){
                    conta.getCliente().exibir();
                }else{
                    System.err.println("Número de Conta ou senha de Cliente incorretos!");
                    System.out.println("Login mal-sucedido");
                }
                exibirTelaPerfil();
            }
            case 2 ->{
                login = Tela.login();
                if(login != null){
                    login.exibir();
                }else{
                    System.out.println("Login mal-sucedido");
                }
                exibirTelaPerfil();
            }
            case 3 -> {
                login = Tela.login();
                if(login != null){
                    ArrayList<Contato> contatos = login.getCliente().getContatos();
                    int inputAlteracaoContato, tipoAlteracaoContato;
                    String novoDado;

                    System.out.println("Selecione um contato para alterar:");
                    for(int i=0;i<contatos.size();i++){
                        System.out.printf("[%d] Telefone: %s; Email: %s\n", (i+1), contatos.get(i).getTelefone(), contatos.get(i).getEmail());
                    }
                    inputAlteracaoContato = Integer.parseInt(scanner.nextLine());

                    if(inputAlteracaoContato > 0 && inputAlteracaoContato <= contatos.size()){

                        System.out.println("Selecione a alteração que quer fazer no Contato:");
                        System.out.println("[1] Telefone:");
                        System.out.println("[2] Email:");
                        System.out.println("[3] Cancelar - Voltar para tela de perfil");

                        tipoAlteracaoContato = Integer.parseInt(scanner.nextLine());
                        if (tipoAlteracaoContato < 1 || tipoAlteracaoContato >= 3){
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
                            contatos.get(inputAlteracaoContato-1).alterarDado(tipoContato.toLowerCase(), novoDado);
                        }
                    }else{
                        System.out.println("Nenhum contato selecionado!");
                    }

                }else{
                    System.out.println("Login mal-sucedido\n");
                }
                exibirTelaPerfil();
            }
            case 4 -> {
                login = Tela.login();
                if(login != null){
                    ArrayList<Endereco> enderecos = login.getCliente().getEnderecos();
                    int inputAlteracaoEndereco, tipoAlteracaoEndereco;
                    String novoDado;

                    System.out.println("Selecione um endereço para alterar:");
                    for(int i=0;i<enderecos.size();i++){
                        System.out.printf("[%d] Logradouro: %s; Cidade: %s; Estado: %s; País: %s; CEP: %s", (i+1), enderecos.get(i).getLogradouro(), enderecos.get(i).getCidade(), enderecos.get(i).getEstado(), enderecos.get(i).getPais(), enderecos.get(i).getCep());
                    }

                    inputAlteracaoEndereco = Integer.parseInt(scanner.nextLine());

                    if(inputAlteracaoEndereco > 0 && inputAlteracaoEndereco <= enderecos.size()){

                        System.out.println("Selecione a alteração que quer fazer no Contato:");
                        System.out.println("[1] Logradouro:");
                        System.out.println("[2] Cidade:");
                        System.out.println("[3] Estado:");
                        System.out.println("[4] País:");
                        System.out.println("[5] CEP:");
                        System.out.println("[6] Cancelar - Voltar para tela de perfil");

                        tipoAlteracaoEndereco = Integer.parseInt(scanner.nextLine());
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
                            System.out.println("Insira o novo ["+tipoEndereco+"]:");
                            novoDado = scanner.nextLine();
                            enderecos.get(inputAlteracaoEndereco-1).alterarDado(tipoEndereco.toLowerCase(), novoDado);
                        }
                    }else{
                        System.out.println("Nenhum endereço selecionado!");
                    }

                }else{
                    System.out.println("Login mal-sucedido\n");
                }
                exibirTelaPerfil();
            }
            case 5 -> {
                login = Tela.login();
                if(login != null) {
                    ArrayList<Contato> contatos = login.getCliente().getContatos();
                    int inputExclusaoContato;

                    if(contatos.size()>1){
                        System.out.println("Selecione um contato para deletar:");
                        for (int i = 0; i < contatos.size(); i++) {
                            System.out.printf("[%d] Telefone: %s; Email: %s", (i + 1), contatos.get(i).getTelefone(), contatos.get(i).getEmail());
                        }

                        inputExclusaoContato = Integer.parseInt(scanner.nextLine());

                        if (inputExclusaoContato > 0 && inputExclusaoContato <= contatos.size()) {
                            System.out.printf("Contato [%d] excluído!", inputExclusaoContato);
                            login.getCliente().removerContato(inputExclusaoContato-1);
                        }else{
                            System.out.println("Nenhum contato selecionado!");
                        }
                    }else{
                        System.out.println("Você tem apenas [1] contato e não pode excluí-lo!");
                    }
                }else{
                    System.out.println("Login mal-sucedido\n");
                }
                exibirTelaPerfil();
            }
            case 6 -> {
                login = Tela.login();
                if(login != null) {
                    ArrayList<Endereco> enderecos = login.getCliente().getEnderecos();
                    int inputExclusaoEndereco;

                    if(enderecos.size() > 1){
                        System.out.println("Selecione um contato para deletar:");
                        for (int i = 0; i < enderecos.size(); i++) {
                            System.out.printf("[%d] Logradouro: %s; Cidade: %s; Estado: %s; País: %s; CEP: %s", (i+1), enderecos.get(i).getLogradouro(), enderecos.get(i).getCidade(), enderecos.get(i).getEstado(), enderecos.get(i).getPais(), enderecos.get(i).getCep());
                        }

                        inputExclusaoEndereco = Integer.parseInt(scanner.nextLine());

                        if (inputExclusaoEndereco > 0 && inputExclusaoEndereco <= enderecos.size()) {
                            System.out.printf("Contato [%d] excluído!", inputExclusaoEndereco);
                            login.getCliente().removerEndereco(inputExclusaoEndereco-1);
                        }else{
                            System.out.println("Nenhum endereço selecionado!");
                        }
                    }else{
                        System.out.println("Você tem apenas [1] endereço e não pode exluí-lo!");
                    }
                }else{
                    System.out.println("Login mal-sucedido\n");
                }
                exibirTelaPerfil();
            }
            case 7 -> {
                login = Tela.login();
                if(login != null) {
                    String contatoInput = "";
                    ArrayList<Contato> contatos = new ArrayList<>();
                    while(!contatoInput.equalsIgnoreCase("ENCERRAR CONTATOS")){
                        System.out.println("Insira [ENCERRAR CONTATOS] para parar de adicionar contatos do cliente");
                        System.out.println("Insira o telefone do contato do cliente:");
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
                            System.out.println("Insira o email do Contato do cliente");
                            email = scanner.nextLine();
                            contatos.add(new Contato(contatoInput, email));
                            System.out.println("\tContato adicionado!");
                        }
                    }
                    login.getCliente().addContatos(contatos);
                }else{
                    System.out.println("Login mal-sucedido\n");
                }
                exibirTelaPerfil();
            }
            case 8 -> {
                login = Tela.login();
                if(login != null) {
                    String enderecoInput = "";
                    ArrayList<Endereco> enderecos = new ArrayList<>();
                    while(!enderecoInput.equalsIgnoreCase("ENCERRAR ENDEREÇOS")){
                        System.out.println("Insira [ENCERRAR ENDEREÇOS] para parar de adicionar endereços do cliente");
                        System.out.println("Insira o Logradouro do Endereço do cliente:");
                        enderecoInput = scanner.nextLine();

                        String cidade, estado, pais, cep;

                        if(enderecoInput.equalsIgnoreCase("ENCERRAR ENDEREÇOS")){
                            if (enderecos.size()<1){
                                System.out.println("Você deve adicionar ao menos um Endereço!");
                                enderecoInput = "";
                            }else{
                                break;
                            }
                        }else{
                            System.out.println("Insira a Cidade do Endereço do cliente:");
                            cidade = scanner.nextLine();
                            System.out.println("Insira o Estado do Endereço do cliente:");
                            estado = scanner.nextLine();
                            System.out.println("Insira o País do Endereço do cliente:");
                            pais = scanner.nextLine();
                            System.out.println("Insira o CEP do Endereço do cliente:");
                            cep = scanner.nextLine();
                            enderecos.add(new Endereco(enderecoInput, cidade, estado, pais, cep));
                            System.out.println("\tEndereço adicionado!");
                        }
                    }
                    login.getCliente().addEnderecos(enderecos);
                }else{
                    System.out.println("Login mal-sucedido\n");
                }
                exibirTelaPerfil();
            }
            case 9 ->{
                String cpfOuNumero, senhaAntiga, novaSenha;

                System.out.println("Insira o CPF do CLIENTE ou o NÚMERO da CONTA:");
                cpfOuNumero = scanner.nextLine();

                if(BancoDeDados.consultarNumeroDaConta(cpfOuNumero) != null || BancoDeDados.consultarExistenciaPorCPF(cpfOuNumero) != null){
                    System.out.println("Insira a SENHA ANTIGA:");
                    senhaAntiga = scanner.nextLine();
                    Conta conta = (BancoDeDados.consultarNumeroDaConta(cpfOuNumero) != null)? BancoDeDados.consultarNumeroDaConta(cpfOuNumero):BancoDeDados.consultarExistenciaPorCPF(cpfOuNumero);
                    if(conta.verificarSenha(senhaAntiga)){
                        System.out.println("Insira a nova senha:");
                        novaSenha = scanner.nextLine();
                        conta.alterarSenha(senhaAntiga, novaSenha);
                    }else{
                        System.err.println("Senha incorreta!");
                        System.err.println("Se não lembra a sua SENHA fale com os ADMINISTRADORES do BANCO para resolver seu problema!");
                    }
                }else{
                    System.err.println("CPF do CLIENTE ou NÚMERO da CONTA incorreto!");
                }
                exibirTelaPerfil();
            }
            case 10 -> Tela.redirecionarParaTela(1);
            default -> {
                System.out.println("Opção inválida!");
                exibirTelaPerfil();
            }
        }
    }

    public static int pedirInput() {
        System.out.println("[1] -> Insira seus dados de LOGIN para exibir seus dados de CLIENTE\n[2] -> Insira seus dados de LOGIN para exibir os dados da sua CONTA\n[3] -> Alterar CONTATO do CLIENTE\n[4] -> Alterar ENDEREÇO do CLIENTE\n[5] -> Deletar CONTATO do CLIENTE\n[6] -> Deletar ENDEREÇO do CLIENTE\n[7] -> Cadastrar novo CONTATO em CLIENTE\n[8] -> Cadastrar novo ENDEREÇO em CLIENTE\n[9] -> Alterar SENHA\n[10] -> Voltar para a Tela Principal");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }
}
