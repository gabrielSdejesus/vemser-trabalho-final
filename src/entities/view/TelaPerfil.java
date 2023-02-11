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
                    ArrayList<Contato> contatos;
                    ArrayList<Endereco> enderecos;
                    Cliente cliente = new Cliente("nome", "cpf", enderecos, contatos, "login", "senha");
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
