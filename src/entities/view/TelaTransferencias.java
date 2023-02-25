package entities.view;

import entities.model.Conta;
import entities.service.ContaService;
import entities.service.TransferenciaService;

public class TelaTransferencias extends Tela {
    public static void exibirTransferencias(){
        System.out.println("Você está na Tela de Transferências");
        TelaTransferencias.tratarInput();
    }

    public static void tratarInput() {
        int input = TelaTransferencias.pedirInput("[1] -> Insira seus dados de login para VISUALIZAR suas TRANSFERÊNCIAS\n[2] -> TRANSFERIR para outra conta\n[3] -> Voltar para a Tela Principal");
        TransferenciaService transferenciaService = new TransferenciaService();
        ContaService contaService = new ContaService();
        Conta login;
        switch(input){
            case 1 ->{
                login = Tela.login();
                if(login != null){
                    transferenciaService.listarTransferenciasPorConta(login.getNumeroConta());
                }else{
                    System.err.println("Login mal-sucedido");
                }
                exibirTransferencias();
            }
            case 2 -> {
                login = Tela.login();
                if(login != null){
                    contaService.transferir(login);
                }else{
                    System.err.println("Login mal-sucedido");
                }
                exibirTransferencias();
            }
            case 3 -> Tela.redirecionarParaTela(1);
            default -> {
                System.err.println("Opção inválida!");
                exibirTransferencias();
            }
        }
    }
}
