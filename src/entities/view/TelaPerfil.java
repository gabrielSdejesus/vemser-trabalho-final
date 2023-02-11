package entities.view;

import entities.controller.BancoDeDados;
import entities.interfaces.Tela;
import entities.model.Cliente;
import entities.model.Conta;
import entities.model.Contato;
import entities.model.Endereco;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class TelaPerfil  implements Tela {
    public static void exibirTelaPerfil(){
        System.out.println("Você está na Tela de Perfil");
        TelaPerfil.tratarInput(TelaPerfil.pedirInput());
    }

    public static void tratarInput(int input) {
        switch(input){
            case 1 ->{
                Scanner scanner = new Scanner(System.in);
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
                Conta login = Tela.login();
                if(login != null){
                    login.exibir();
                }else{
                    System.out.println("Login mal-sucedido");
                }
                exibirTelaPerfil();
            }
            case 3 ->{
                Scanner scanner = new Scanner(System.in);
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
                            break;
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
                        System.out.println("Insira o logradouro do endereço:");
                        enderecoInput = scanner.nextLine();

                        String cidade, estado, pais, cep;

                        if(enderecoInput.equalsIgnoreCase("ENCERRAR CONTATOS")){
                            break;
                        }else{
                            System.out.println("Insira a cidade do Endereço");
                            cidade = scanner.nextLine();
                            System.out.println("Insira o estado do Endereço");
                            estado = scanner.nextLine();
                            System.out.println("Insira o país do Endereço");
                            pais = scanner.nextLine();
                            System.out.println("Insira o cep do Endereço");
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
            case 4 -> Tela.redirecionarParaTela(1);
            default -> {
                System.out.println("Opção inválida!");
                exibirTelaPerfil();
            }
        }
    }

    public static int pedirInput() {
        System.out.println("[1] -> Insira seus dados de login para exibir seus dados de cliente\n[2] -> Insira seus dados de login para exibir os dados da sua conta\n[3] -> Cadastrar um novo Cliente com Conta\n[4] -> Voltar para a Tela Principal");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }
}
