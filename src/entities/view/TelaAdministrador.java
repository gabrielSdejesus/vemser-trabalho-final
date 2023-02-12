package entities.view;

import entities.controller.BancoDeDados;
import entities.interfaces.Tela;
import entities.model.Cliente;
import entities.model.Conta;
import entities.model.Contato;
import entities.model.Endereco;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

import static entities.controller.BancoDeDados.getContas;

public class TelaAdministrador implements Tela {
    public static void exibirTelaAdministrador(){
        System.out.println("Você está na Tela de Administrador");
        TelaAdministrador.tratarInput(TelaAdministrador.pedirInput());
    }

    public static void tratarInput(int input) {
        Scanner scanner = new Scanner(System.in);
        String senhaAdm;
        switch(input){
            case 1 ->{
                System.out.println("Insira a senha Administrativa [ABACAXI]:");
                senhaAdm = scanner.nextLine();

                if(senhaAdm.equals("ABACAXI")){
                    String nomeCliente, cpfCliente, senhaCliente;

                    System.out.println("\tEtapa [1] de [5]");
                    System.out.println("Insira o Nome do cliente:");
                    nomeCliente = scanner.nextLine();

                    System.out.println("\tEtapa [2] de [5]");
                    System.out.println("Insira o CPF do cliente:");
                    cpfCliente = scanner.nextLine();

                    System.out.println("\tEtapa [3] de [5]");
                    System.out.println("Insira a Senha do cliente:");
                    senhaCliente = scanner.nextLine();

                    ArrayList<Contato> contatos = new ArrayList<>();
                    String contatoInput = "";

                    System.out.println("\tEtapa [4] de [5]");
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

                    ArrayList<Endereco> enderecos = new ArrayList<>();
                    String enderecoInput = "";

                    System.out.println("\tEtapa [5] de [5]");
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
                    Cliente cliente = new Cliente(nomeCliente, cpfCliente, enderecos, contatos, senhaCliente);
                    Conta conta = new Conta(cliente, 0, senhaCliente);
                    System.out.println("Cliente adicionado com sucesso!");
                    System.out.println("\tNÃO ESQUECA DE GUARDAR OS DADOS DE ACESSO DA CONTA E DO CLIENTE!");
                    System.out.println("\t\tNúmero da conta do cliente: "+conta.getNumero());
                    System.out.println("\t\tSenha do cliente: "+senhaCliente);
                }else{
                    System.err.println("Senha administrativa inválida!");
                }
                exibirTelaAdministrador();
            }
            case 2 -> {
                System.out.println("Insira a senha Administrativa [ABACAXI]:");
                senhaAdm = scanner.nextLine();
                String cpfCliente;

                if(senhaAdm.equals("ABACAXI")){
                    System.err.println("Atenção! Deletar o CLIENTE também deletará sua CONTA");
                    System.out.println("Insira o CPF do CLIENTE que quer deletar:");
                    cpfCliente = scanner.nextLine();
                    if(BancoDeDados.consultarExistenciaPorCPF(cpfCliente).getSaldo() > 0){
                        String confirmacao;
                        System.out.println("Esse CLIENTE ainda tem saldo em sua conta, tem certeza dessa operação? [Y/N]");
                        confirmacao = scanner.nextLine();
                        if(confirmacao.equalsIgnoreCase("Y")){
                            System.out.println("\tContinuando operação!");
                        }else{
                            System.err.println("\tOperação encerrada!");
                            break;
                        }
                    }

                    if(BancoDeDados.deletarCliente(cpfCliente)){
                        System.out.println("CLIENTE e CONTA do CLIENTE deletados com sucesso!");
                    }else{
                        System.err.println("CPF informado não encontrado no Banco De Dados!");
                        System.err.println("Operação não realizada!");
                    }
                }else{
                    System.err.println("Senha administrativa inválida!");
                }
                exibirTelaAdministrador();
            }
            case 3 -> {
                System.out.println("Insira a senha Administrativa [ABACAXI]:");
                senhaAdm = scanner.nextLine();
                String numeroConta;

                if(senhaAdm.equals("ABACAXI")){
                    System.err.println("Atenção! Deletar uma CONTA também deletará seu CLIENTE");
                    System.out.println("Contas cadastradas");
                    for(Conta conta : BancoDeDados.getContas()) {
                        System.out.printf("\tCliente: %s | CPF: %s | Nº da conta %d",
                                conta.getCliente().getNome(), conta.getCliente().getCpf(), conta.getNumero());
                    }
                    System.out.println("\nInsira o NÚMERO da CONTA que quer deletar:");
                    numeroConta = scanner.nextLine();
                    if(BancoDeDados.consultarNumeroDaConta(numeroConta).getSaldo() > 0){
                        String confirmacao;
                        System.out.println("Essa CONTA ainda tem saldo, tem certeza dessa operação? [Y/N]");
                        confirmacao = scanner.nextLine();
                        if(confirmacao.equalsIgnoreCase("Y")){
                            System.out.println("\tContinuando operação!");
                        }else{
                            System.err.println("\tOperação encerrada!");
                            break;
                        }
                    }

                    if(BancoDeDados.deletarConta(Integer.parseInt(numeroConta))){
                        System.out.println("CONTA e CLIENTE deletados com sucesso!");
                    }else{
                        System.err.println("NÚMERO de CONTA informado não encontrado no Banco De Dados!");
                        System.err.println("Operação não realizada!");
                    }
                }else{
                    System.err.println("Senha administrativa inválida!");
                }
                exibirTelaAdministrador();
            }
            case 4 -> Tela.redirecionarParaTela(1);
            default -> {
                System.out.println("Opção inválida!");
                exibirTelaAdministrador();
            }
        }
    }

    public static int pedirInput() {
        System.out.println("[1] -> Cadastrar um novo CLIENTE com CONTA\n[2] -> Deletar CLIENTE\n[3] -> Deletar CONTA\n[4] -> Voltar para a Tela Principal");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }
}
