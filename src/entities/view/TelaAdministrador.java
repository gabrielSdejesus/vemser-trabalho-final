package entities.view;

import entities.service.ClienteService;
import entities.service.ContaService;

import java.util.Scanner;

public class TelaAdministrador extends Tela {
    public static void exibirTelaAdministrador(){
        System.out.println("Você está na Tela de Administrador");
        TelaAdministrador.tratarInput(TelaAdministrador.pedirInput());
    }

    public static void tratarInput(int input) {
        Scanner scanner = new Scanner(System.in);
        switch(input){
            case 1 ->{
                if(Tela.loginAdm()){
                    ClienteService.cadastrarCliente();
                }else{
                    System.err.println("Senha administrativa inválida!\n");
                }
                exibirTelaAdministrador();
            }
            case 2 -> {
                if(Tela.loginAdm()){
                    ClienteService.deletarCliente();
                }else{
                    System.err.println("Senha administrativa inválida!");
                }
                exibirTelaAdministrador();
            }
            case 3 -> {
                if(Tela.loginAdm()){
                    ContaService.deletarConta();
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
